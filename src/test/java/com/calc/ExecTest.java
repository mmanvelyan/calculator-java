package com.calc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExecTest {

    private final QueryParser expressionParser = new QueryParser();
    private final Variables variables = new Variables();

    private String rpn(String s) throws ReflectiveOperationException {
        return expressionParser.parse(s).execute(variables).getStr();
    }

    private float eval(String s) throws ReflectiveOperationException {
        return expressionParser.parse(s).execute(variables).getVal();
    }

    @Test
    public void evalNoCommand() throws ReflectiveOperationException {
        assertEquals(25.0, eval("42/6+(70-64)*5+52/26-70/5"));
    }

    @Test
    public void evalCommand() throws ReflectiveOperationException {
        assertEquals(25.0, eval("eval # 42/6+(70-64)*5+52/26-70/5"));
    }

    @Test
    public void unexpectedCommand() {
        assertThrows(UnexpectedCommandException.class, ()->eval("prn # 2-3"));
    }

    @Test
    public void unexpectedToken() {
        UnexpectedTokenException thrown = assertThrows(UnexpectedTokenException.class, ()->rpn("rpn # 2+*3"));
        assertEquals(8, thrown.getPos());
        assertEquals(Type.MUL, thrown.getToken().getType());
    }

    @Test
    public void rpnCommand() throws ReflectiveOperationException {
        assertEquals("2.0 3.0 + ", rpn("rpn # 2+3"));
    }

}









