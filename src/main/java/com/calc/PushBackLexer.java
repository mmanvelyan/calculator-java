package com.calc;

import java.util.LinkedList;

public class PushBackLexer implements Lexer {
    private final Lexer lex;
    private final LinkedList<Token> prevTokens;
    private int rollbackLevel = 0;
    private final int maxRollback;

    public PushBackLexer(Lexer lex, int maxRollback) {
        this.lex = lex;
        this.maxRollback = maxRollback;
        prevTokens = new LinkedList<>();
    }

    public PushBackLexer(Lexer lex){
        this(lex, 1);
    }

    public Token nextToken(){
        Token nxt;
        if (rollbackLevel == 0){
            nxt = lex.nextToken();
            prevTokens.addFirst(nxt);
            if (prevTokens.size() > maxRollback){
                prevTokens.removeLast();
            }
        } else {
            nxt = prevTokens.get(rollbackLevel-1);
            rollbackLevel--;
        }
        return nxt;
    }

    public void returnToPrevPos(){
        if (rollbackLevel == maxRollback){
            throw new RollbackLevelException("Rollback level exceeded");
        }
        rollbackLevel++;
    }
}
