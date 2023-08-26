package com.calc.lexer;

public class BookmarkLexer extends PushBackLexer {

    private int tokenCount = 0;

    public BookmarkLexer(Lexer lex) {
        super(lex, Integer.MAX_VALUE);
    }

    public void setBookmark(){
        tokenCount = 0;
    }

    public Token nextToken(){
        tokenCount++;
        return super.nextToken();
    }

    public void returnToPrevPos(){
        this.returnToPrevPos(1);
    }

    public void returnToPrevPos(int count){
        tokenCount -= count;
        super.returnToPrevPos(count);
    }

    public void returnToBookmark(){
        super.returnToPrevPos(tokenCount);
        tokenCount = 0;
    }

}

