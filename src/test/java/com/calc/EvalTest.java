package com.calc;

import com.calc.command.EvalNodeVisitor;
import com.calc.command.PrintNodeVisitor;
import com.calc.command.Result;
import com.calc.command.ResultType;
import com.calc.node.Node;
import com.calc.parser.MathExpressionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EvalTest {

    private String calculate(String s){
        Variables variables = new Variables();
        Functions functions = new Functions();
        return calculate(s, variables, functions);
    }

    private String calculate(String s, Variables variables, Functions functions){
        MathExpressionParser calc = new MathExpressionParser();
        Node tree = calc.parse(s);
        EvalNodeVisitor eval = new EvalNodeVisitor();
        Result resultExpression = tree.accept(eval, variables, functions);
        if (resultExpression.getType() == ResultType.VAL){
            return String.valueOf(resultExpression.getVal());
        }
        Result result = resultExpression.getExpression().accept(new PrintNodeVisitor(), variables, functions);
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
        Variables variables = new Variables();
        Functions functions = new Functions();
        Assertions.assertEquals("3.0", calculate("x = 3", variables, functions));
        Assertions.assertEquals("5.0", calculate("x+2", variables, functions));
    }

    @Test
    public void evalFunctionArgument() {
        Variables variables = new Variables();
        Functions functions = new Functions();
        Assertions.assertEquals("3.0", calculate("x = 3", variables, functions));
        Assertions.assertEquals("f(5)", calculate("f(x+2)", variables, functions));
    }

    @Test
    public void evalStrictFunction() {
        Variables variables = new Variables();
        Functions functions = new Functions();
        Assertions.assertEquals("x^2", calculate("f(x) = x^2", variables, functions));
        Assertions.assertEquals("4.0", calculate("f(2)", variables, functions));
    }

    @Test
    public void evalUnknownArgument(){
        Variables variables = new Variables();
        Functions functions = new Functions();
        Assertions.assertEquals("x^2", calculate("f(x) = x^2", variables, functions));
        Assertions.assertEquals("y^2", calculate("f(y)", variables, functions));
    }

    @Test
    public void printCorrectBrackets(){
        Variables variables = new Variables();
        Functions functions = new Functions();
        Assertions.assertEquals("(x+y)*z", calculate("(x+y)*z", variables, functions));
    }

}
