package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static com.calc.Type.*;

public class PushBackLexerTest {
    private PushBackLexer lex;

    @Test
    public void nextTokenADD(){
        lex = new PushBackLexer(new BaseLexer("+"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(ADD, nxt.getType());
    }

    @Test
    public void nextTokenSUB(){
        lex = new PushBackLexer(new BaseLexer("-"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(SUB, nxt.getType());
    }

    @Test
    public void nextTokenMUL(){
        lex = new PushBackLexer(new BaseLexer("*"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(MUL, nxt.getType());
    }

    @Test
    public void nextTokenDIV(){
        lex = new PushBackLexer(new BaseLexer("/"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(DIV, nxt.getType());
    }

    @Test
    public void nextTokenASS(){
        lex = new PushBackLexer(new BaseLexer("="));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(ASS, nxt.getType());
    }

    @Test
    public void nextTokenOpenBr(){
        lex = new PushBackLexer(new BaseLexer("("));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(OPEN_BR, nxt.getType());
    }

    @Test
    public void nextTokenClosingBr(){
        lex = new PushBackLexer(new BaseLexer(")"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(CLOSING_BR, nxt.getType());
    }

    @Test
    public void nextTokenEND(){
        lex = new PushBackLexer(new BaseLexer(""));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(END, nxt.getType());
    }

    @Test
    public void nextTokenINV(){
        lex = new PushBackLexer(new BaseLexer("#"));
        Assertions.assertThrows(UnexpectedTokenException.class, () -> lex.nextToken());
    }

    @Test
    public void nextTokenNUM(){
        lex = new PushBackLexer(new BaseLexer("5.678"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(NUM, nxt.getType());
        float e = Math.abs(nxt.getVal()-5.678F);
        Assertions.assertTrue(e < 0.001);
    }

    @Test
    public void nextTokenVAR(){
        lex = new PushBackLexer(new BaseLexer("xX374y"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(VAR, nxt.getType());
        Assertions.assertEquals("xX374y", nxt.getName());
    }

    @Test
    public void nextTokenCorrectPosition(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"));
        Token nxt = lex.nextToken();
        Assertions.assertEquals(0, nxt.getPos());
        nxt = lex.nextToken();
        Assertions.assertEquals(1, nxt.getPos());
        nxt = lex.nextToken();
        Assertions.assertEquals(2, nxt.getPos());
        nxt = lex.nextToken();
        Assertions.assertEquals(3, nxt.getPos());
        nxt = lex.nextToken();
        Assertions.assertEquals(4, nxt.getPos());
        nxt = lex.nextToken();
        Assertions.assertEquals(7, nxt.getPos());
        nxt = lex.nextToken();
        Assertions.assertEquals(11, nxt.getPos());
    }

    @Test
    public void rollbackException(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"), 0);
        Token nxt = lex.nextToken();
        Assertions.assertThrows(RollbackLevelException.class, () -> lex.returnToPrevPos());
    }

    @Test
    public void rollbackTest1(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"), 1);
        Token nxt = lex.nextToken();
        Assertions.assertEquals(ADD, nxt.getType());
        lex.returnToPrevPos();
        nxt = lex.nextToken();
        Assertions.assertEquals(ADD, nxt.getType());
    }

    @Test
    public void rollbackTest2(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"), 2);
        Token nxt = lex.nextToken();
        nxt = lex.nextToken();
        lex.returnToPrevPos();
        lex.returnToPrevPos();
        nxt = lex.nextToken();
        Assertions.assertEquals(ADD, nxt.getType());
        nxt = lex.nextToken();
        Assertions.assertEquals(SUB, nxt.getType());
    }
}