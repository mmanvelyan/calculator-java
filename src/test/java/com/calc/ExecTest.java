package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExecTest {

    private final QueryParser queryParser = new QueryParser();
    private final Variables variables = new Variables();

    private void assertEquals(Result a, Result b){
        Assertions.assertEquals(a.getType(), b.getType());
        Assertions.assertEquals(a.getStr(), b.getStr());
        Assertions.assertEquals(a.getVal(), b.getVal());
    }

    private Result execute(String s){
        Query query = queryParser.parse(s);
        Command command = query.getCommand();
        Node expression = query.getExpression();
        return command.execute(expression, variables);
    }

    @Test
    public void evalNoCommand() {
        assertEquals(new Result(5.0F), execute("2+3"));
    }

    @Test
    public void evalCommand() {
        assertEquals(new Result(5.0F), execute("eval # 2+3"));
    }

    @Test
    public void unexpectedCommand() {
        assertThrows(UnexpectedCommandException.class, ()-> execute("prn # 2-3"));
    }

    @Test
    public void unexpectedToken() {
        UnexpectedTokenException thrown = assertThrows(UnexpectedTokenException.class, ()->execute("rpn # 2+*3"));
        Assertions.assertEquals(8, thrown.getPos());
        Assertions.assertEquals(Type.MUL, thrown.getToken().getType());
    }

    @Test
    public void rpnCommand() {
        assertEquals(new Result("2.0 3.0 + "), execute("rpn # 2+3"));
    }

}









