package com.calc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PushBackLexerTest {
    private PushBackLexer lex;

    @Test
    public void nextTokenADD(){
        lex = new PushBackLexer(new BaseLexer("+"));
        Token nxt = lex.nextToken();
        assertEquals(Type.ADD, nxt.getType());
    }

    @Test
    public void nextTokenEND(){
        lex = new PushBackLexer(new BaseLexer(""));
        Token nxt = lex.nextToken();
        assertEquals(Type.END, nxt.getType());
    }

    @Test
    public void nextTokenINV(){
        lex = new PushBackLexer(new BaseLexer("#"));
        assertThrows(UnexpectedTokenException.class, () -> lex.nextToken());
    }

    @Test
    public void nextTokenNUM(){
        lex = new PushBackLexer(new BaseLexer("5.678"));
        Token nxt = lex.nextToken();
        assertEquals(Type.NUM, nxt.getType());
        assertEquals(5.678F, nxt.getVal(), 0.001F);
    }

    @Test
    public void nextTokenVAR(){
        lex = new PushBackLexer(new BaseLexer("xX374y"));
        Token nxt = lex.nextToken();
        assertEquals(Type.VAR, nxt.getType());
        assertEquals("xX374y", nxt.getName());
    }

    @Test
    public void nextTokenCorrectPosition(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"));
        Token nxt = lex.nextToken();
        assertEquals(0, nxt.getPos());
        nxt = lex.nextToken();
        assertEquals(1, nxt.getPos());
        nxt = lex.nextToken();
        assertEquals(2, nxt.getPos());
        nxt = lex.nextToken();
        assertEquals(3, nxt.getPos());
        nxt = lex.nextToken();
        assertEquals(4, nxt.getPos());
        nxt = lex.nextToken();
        assertEquals(7, nxt.getPos());
        nxt = lex.nextToken();
        assertEquals(11, nxt.getPos());
    }

    @Test
    public void rollbackLimitException(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"), 0);
        Token nxt = lex.nextToken();
        assertThrows(RollbackLevelException.class, () -> lex.returnToPrevPos());
    }

    @Test
    public void oneTokenRollbackTest(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"), 1);
        Token nxt = lex.nextToken();
        assertEquals(Type.ADD, nxt.getType());
        lex.returnToPrevPos();
        nxt = lex.nextToken();
        assertEquals(Type.ADD, nxt.getType());
    }

    @Test
    public void multipleTokensRollbackTest(){
        lex = new PushBackLexer(new BaseLexer("+-*/=5.2 xyz"), 2);
        lex.nextToken();
        lex.nextToken();
        lex.returnToPrevPos();
        lex.returnToPrevPos();
        Token nxt = lex.nextToken();
        assertEquals(Type.ADD, nxt.getType());
        nxt = lex.nextToken();
        assertEquals(Type.SUB, nxt.getType());
    }
}