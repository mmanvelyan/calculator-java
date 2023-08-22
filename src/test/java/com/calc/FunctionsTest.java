package com.calc;

import com.calc.command.*;
import com.calc.lexer.Type;
import com.calc.node.Node;
import com.calc.parser.Query;
import com.calc.parser.QueryParser;
import com.calc.lexer.UnexpectedTokenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionsTest {

    private final Variables variables = new Variables();
    private final Functions functions = new Functions();

    private void assertEquals(float expected, Result actual){
        Assertions.assertEquals(expected, actual.getVal());
    }

    private void assertEquals(String expected, Result actual){
        Assertions.assertEquals(expected, actual.getStr());
    }

    private Result execute(String s){
        QueryParser queryParser = new QueryParser();
        Query query = queryParser.parse(s);
        NodeVisitor command = query.getCommand();
        Node expression = query.getExpression();
        Result result = expression.accept(command, variables, functions);
        if (result.getType() == ResultType.EXP){
            return result.getExpression().accept(new PrintNodeVisitor(), variables, functions);
        }
        return result;
    }

    @Test
    public void unexpectedFunctionName(){
        
        UnexpectedFunctionException thrown = assertThrows(UnexpectedFunctionException.class, () -> execute("f(x) = g(x)"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("g", thrown.getName());
    }

    @Test
    public void unexpectedFunctionArguments(){
        assertEquals("x", execute("f(x) = x"));
        UnexpectedFunctionException thrown = assertThrows(UnexpectedFunctionException.class, () -> execute("eval strict # f(1, 2)"));
        Assertions.assertEquals(14, thrown.getPos());
        Assertions.assertEquals("f", thrown.getName());
    }

    @Test
    public void unexpectedArgument(){
        UnexpectedTokenException thrown = assertThrows(UnexpectedTokenException.class, () -> execute("f(x+y) = g(x)"));
        Assertions.assertEquals(7, thrown.getPos());
    }

    @Test
    public void functionCycle(){
        
        assertEquals("1", execute("g(x) = 1"));
        assertEquals("g(x)", execute("f(x) = g(x)"));
        FunctionCycleException thrown = assertThrows(FunctionCycleException.class, () -> execute("g(x) = f(x)"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("g", thrown.getName());
    }

    @Test
    public void unexpectedToken(){
        
        UnexpectedTokenException thrown = assertThrows(UnexpectedTokenException.class, () -> execute("f(x = 2)"));
        Assertions.assertEquals(4, thrown.getPos());
        Assertions.assertEquals(Type.ASS, thrown.getToken().getType());
    }

    @Test
    public void unexpectedVariable(){
        
        assertEquals(5, execute("y = 5"));
        UnexpectedVariableException thrown = assertThrows(UnexpectedVariableException.class, () -> execute("f(x) = y"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("y", thrown.getName());
    }

    @Test
    public void functionTest(){
        
        assertEquals("x*x", execute("f(x) = x*x"));
        assertEquals(25, execute("f(5)"));
    }

    @Test
    public void variableArgument(){
        
        assertEquals("x*x", execute("f(x) = x*x"));
        assertEquals(5, execute("y = 5"));
        assertEquals(25, execute("f(y)"));
    }

    @Test
    public void expressionArgument(){
        
        assertEquals("x*x", execute("f(x) = x*x"));
        assertEquals(5, execute("y = 5"));
        assertEquals(100, execute("f(2*y)"));
    }

    @Test
    public void functionArgument(){
        
        assertEquals("x*x", execute("f(x) = x*x"));
        assertEquals(16, execute("f(f(2))"));
    }

    @Test
    public void multipleArguments(){
        
        assertEquals("x*x+y", execute("f(x, y) = x*x + y"));
        assertEquals(9, execute("f(2, 5)"));
    }

    @Test
    public void rpnFunction(){
        assertEquals("2*x", execute("f(x) = 2*x"));
        assertEquals("f(x y + , x y *) g(x) - ", execute("rpn # f(x+y, x*y)-g(x)"));
    }

}
