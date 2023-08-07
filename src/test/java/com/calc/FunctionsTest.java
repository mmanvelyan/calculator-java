package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionsTest {

    private Variables variables = new Variables();
    private Functions functions = new Functions();

    private void clear(){
        variables = new Variables();
        functions = new Functions();
    }

    private void assertEquals(float expected, Result actual){
        Assertions.assertEquals(expected, actual.getVal());
    }

    private void assertEquals(String expected, Result actual){
        Assertions.assertEquals(expected, actual.getStr());
    }

    private Result execute(String s){
        QueryParser queryParser = new QueryParser();
        Query query = queryParser.parse(s);
        Command command = query.getCommand();
        Node expression = query.getExpression();
        return command.execute(expression, variables, functions);
    }

    @Test
    public void unexpectedFunctionName(){
        clear();
        UnexpectedFunctionException thrown = assertThrows(UnexpectedFunctionException.class, () -> execute("f(x) = g(x)"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("g", thrown.getName());
    }

    @Test
    public void unexpectedFunctionArguments(){
        clear();
        assertEquals(0, execute("f(x) = x"));
        UnexpectedFunctionException thrown = assertThrows(UnexpectedFunctionException.class, () -> execute("f(1, 2)"));
        Assertions.assertEquals(0, thrown.getPos());
        Assertions.assertEquals("f", thrown.getName());
    }

    @Test
    public void unexpectedArgument(){
        clear();
        UnexpectedArgumentException thrown = assertThrows(UnexpectedArgumentException.class, () -> execute("f(x+y) = g(x)"));
        Assertions.assertEquals(3, thrown.getPos());
    }

    @Test
    public void functionCycle(){
        clear();
        assertEquals(0, execute("g(x) = 1"));
        assertEquals(0, execute("f(x) = g(x)"));
        FunctionCycleException thrown = assertThrows(FunctionCycleException.class, () -> execute("g(x) = f(x)"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("g", thrown.getName());
    }

    @Test
    public void unexpectedToken(){
        clear();
        UnexpectedTokenException thrown = assertThrows(UnexpectedTokenException.class, () -> execute("x = f(x) = x"));
        Assertions.assertEquals(9, thrown.getPos());
        Assertions.assertEquals(Type.ASS, thrown.getToken().getType());
    }

    @Test
    public void unexpectedVariable(){
        clear();
        assertEquals(5, execute("y = 5"));
        UnexpectedVariableException thrown = assertThrows(UnexpectedVariableException.class, () -> execute("f(x) = y"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("y", thrown.getName());
    }

    @Test
    public void functionTest(){
        clear();
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(25, execute("f(5)"));
    }

    @Test
    public void variableArgument(){
        clear();
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(5, execute("y = 5"));
        assertEquals(25, execute("f(y)"));
    }

    @Test
    public void expressionArgument(){
        clear();
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(5, execute("y = 5"));
        assertEquals(100, execute("f(2*y)"));
    }

    @Test
    public void functionArgument(){
        clear();
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(16, execute("f(f(2))"));
    }

    @Test
    public void multipleArguments(){
        clear();
        assertEquals(0, execute("f(x, y) = x*x + y"));
        assertEquals(9, execute("f(2, 5)"));
    }

    @Test
    public void rpnFunction(){
        clear();
        assertEquals("f(x y + , x y *) g(x) - ", execute("rpn # f(x+y, x*y)-g(x)"));
    }
}
