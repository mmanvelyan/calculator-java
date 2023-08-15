package com.calc.lexer;

import java.util.LinkedList;

import org.apache.log4j.Logger;

public class PushBackLexer implements Lexer {
    private final Lexer lex;
    private final LinkedList<Token> prevTokens;
    private int rollbackLevel = 0;
    private final int maxRollback;

    private final Logger log = Logger.getLogger(PushBackLexer.class);

    public PushBackLexer(Lexer lex, int maxRollback) {
        this.lex = lex;
        this.maxRollback = maxRollback;
        prevTokens = new LinkedList<>();
        log.debug("------------------------------------------------------");
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
        log.debug("nxt : { type = " + nxt.getType().toString()+", pos = " + nxt.getPos() + ", value = " + nxt.getVal() + ", name = " + nxt.getName() + " }");
        return nxt;
    }

    public void returnToPrevPos(){
        returnToPrevPos(1);
    }

    public void returnToPrevPos(int count){
        if (rollbackLevel+count > maxRollback){
            throw new RollbackLevelException("Rollback level exceeded");
        }
        log.debug("rollback "+count);
        rollbackLevel += count;
    }

}
