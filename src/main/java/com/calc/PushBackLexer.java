package com.calc;

import java.util.ArrayList;
import java.util.LinkedList;

public class PushBackLexer implements Lexer {
    private final Lexer lex;
    private final ArrayList<Token> prevTokens;
    private int rollbackLevel = 0;
    private final int maxRollback;

    public PushBackLexer(Lexer lex, int maxRollback) {
        this.lex = lex;
        this.maxRollback = maxRollback;
        prevTokens = new ArrayList<>();
    }

    public PushBackLexer(Lexer lex){
        this(lex, 1);
    }

    public Token nextToken(){
        Token nxt;
        if (rollbackLevel == 0){
            nxt = lex.nextToken();
            prevTokens.add(0, nxt);
            if (prevTokens.size() > maxRollback){
                prevTokens.remove(prevTokens.size()-1);
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
