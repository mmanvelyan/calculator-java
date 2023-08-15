package com.calc;

import com.calc.commands.*;
import com.calc.lexer.Type;
import com.calc.nodes.Node;
import com.calc.parser.Query;
import com.calc.parser.QueryParser;
import com.calc.lexer.UnexpectedTokenException;
import org.junit.jupiter.api.Assertions;
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
        return expression.accept(command, variables, functions);
    }

    @Test
    public void unexpectedFunctionName(){
        
        UnexpectedFunctionException thrown = assertThrows(UnexpectedFunctionException.class, () -> execute("f(x) = g(x)"));
        Assertions.assertEquals(7, thrown.getPos());
        Assertions.assertEquals("g", thrown.getName());
    }

    @Test
    public void unexpectedFunctionArguments(){
        assertEquals(0, execute("f(x) = x"));
        UnexpectedFunctionException thrown = assertThrows(UnexpectedFunctionException.class, () -> execute("f(1, 2)"));
        Assertions.assertEquals(0, thrown.getPos());
        Assertions.assertEquals("f", thrown.getName());
    }

    @Test
    public void unexpectedArgument(){
        UnexpectedTokenException thrown = assertThrows(UnexpectedTokenException.class, () -> execute("f(x+y) = g(x)"));
        Assertions.assertEquals(7, thrown.getPos());
    }

    @Test
    public void functionCycle(){
        
        assertEquals(0, execute("g(x) = 1"));
        assertEquals(0, execute("f(x) = g(x)"));
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
        
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(25, execute("f(5)"));
    }

    @Test
    public void variableArgument(){
        
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(5, execute("y = 5"));
        assertEquals(25, execute("f(y)"));
    }

    @Test
    public void expressionArgument(){
        
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(5, execute("y = 5"));
        assertEquals(100, execute("f(2*y)"));
    }

    @Test
    public void functionArgument(){
        
        assertEquals(0, execute("f(x) = x*x"));
        assertEquals(16, execute("f(f(2))"));
    }

    @Test
    public void multipleArguments(){
        
        assertEquals(0, execute("f(x, y) = x*x + y"));
        assertEquals(9, execute("f(2, 5)"));
    }

    @Test
    public void rpnFunction(){
        assertEquals(0, execute("f(x) = 2*x"));
        assertEquals("f(x y + , x y *) g(x) - ", execute("rpn # f(x+y, x*y)-g(x)"));
    }
}
