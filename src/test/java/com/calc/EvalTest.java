package com.calc;

import com.calc.command.*;
import com.calc.lexer.UnexpectedTokenException;
import com.calc.node.Node;
import com.calc.parser.MathExpressionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.calc.lexer.Type.ASS;

public class EvalTest {

    private String calculate(String s){
        Context context = new Context();
        return calculate(s, context);
    }

    private String calculate(String s, Context context){
        MathExpressionParser calc = new MathExpressionParser();
        Node tree = calc.parse(s);
        EvalNodeVisitor eval = new EvalNodeVisitor();
        Result resultExpression = tree.accept(eval, context);
        if (resultExpression.getType() == ResultType.VAL){
            return String.valueOf(resultExpression.getVal());
        }
        Result result = resultExpression.getExpression().accept(new PrintNodeVisitor(), context);
        return result.getStr();
    }

    @Test
    public void evalStrict() {
        Assertions.assertEquals("5.0", calculate("2+3"));
    }

    @Test
    public void unknownVariable() {
        Assertions.assertEquals("2*x", calculate("2*x"));
    }

    @Test
    public void evalStrictVariable() {
        Context context = new Context();
        Assertions.assertEquals("3.0", calculate("x = 3", context));
        Assertions.assertEquals("5.0", calculate("x+2", context));
    }

    @Test
    public void evalFunctionArgument() {
        Context context = new Context();
        Assertions.assertEquals("3.0", calculate("x = 3", context));
        Assertions.assertEquals("f(5)", calculate("f(x+2)", context));
    }

    @Test
    public void evalStrictFunction() {
        Context context = new Context();
        Assertions.assertEquals("x^2", calculate("f(x) = x^2", context));
        Assertions.assertEquals("4.0", calculate("f(2)", context));
    }

    @Test
    public void evalUnknownArgument(){
        Context context = new Context();
        Assertions.assertEquals("x^2", calculate("f(x) = x^2", context));
        Assertions.assertEquals("y^2", calculate("f(y)", context));
    }

    @Test
    public void variableDefinitionUnexpected(){
        Context context = new Context();
        UnexpectedVariableException thrown = Assertions.assertThrows(UnexpectedVariableException.class, () -> calculate("x = y", context));
        Assertions.assertEquals(4, thrown.getPos());
        Assertions.assertEquals("y", thrown.getName());
    }

    @Test
    public void variableDefinesVariable(){
        Context context = new Context();
        Assertions.assertEquals("5.0", calculate("y = 5", context));
        Assertions.assertEquals("5.0", calculate("x = y", context));
        Assertions.assertEquals("5.0", calculate("x", context));
    }

    @Test
    public void functionDefinitionUnexpected(){
        Context context = new Context();
        UnexpectedFunctionException thrown = Assertions.assertThrows(UnexpectedFunctionException.class, () -> calculate("f(x) = g(x)", context));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("g", thrown.getName());
    }

    @Test
    public void printCorrectBrackets(){
        Context context = new Context();
        Assertions.assertEquals("(x+y)*z", calculate("(x+y)*z", context));
    }

    @Test
    public void multipleDefinitionTests(){
        Context context = new Context();
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("x = f(x) = x", context));
        Assertions.assertEquals(9, thrown.getPos());
        Assertions.assertEquals(ASS, thrown.getToken().getType());
    }
}
