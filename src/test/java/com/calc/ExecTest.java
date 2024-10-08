package com.calc;

import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.lexer.Type;
import com.calc.lexer.UnexpectedTokenException;
import com.calc.node.Node;
import com.calc.parser.Query;
import com.calc.parser.QueryParser;
import com.calc.parser.UnexpectedCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecTest {

    private final QueryParser queryParser = new QueryParser();
    private final Context context = new Context();

    private void assertEquals(Result a, Result b){
        Assertions.assertEquals(a.getType(), b.getType());
        Assertions.assertEquals(a.getStr(), b.getStr());
        Assertions.assertEquals(a.getVal(), b.getVal());
    }

    private Result execute(String s){
        Query query = queryParser.parse(s);
        NodeVisitor command = query.getCommand();
        Node expression = query.getExpression();
        return expression.accept(command, context);
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









