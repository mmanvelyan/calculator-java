package com.calc;

import java.util.ArrayList;

public class LexerPrevTokens {
    private final ArrayList<Token> prevTokens;
    private final ArrayList<Integer> prevPos;

    public LexerPrevTokens(int pos){
        prevTokens = new ArrayList<>();
        prevPos = new ArrayList<>();
        prevPos.add(pos);
        prevTokens.add(new Token(Type.END));
    }

    public LexerPrevTokens(LexerPrevTokens l){
        prevTokens = new ArrayList<>(l.prevTokens);
        prevPos = new ArrayList<>(l.prevPos);
    }
    public void addToken(Token t, int pos){
        prevTokens.add(t);
        prevPos.add(pos);
    }
    public int returnToPrevPos(){
        prevTokens.remove(prevTokens.size()-1);
        prevPos.remove(prevPos.size()-1);
        if (prevPos.size() == 0){
            return 0;
        }
        return prevPos.get(prevPos.size()-1);
    }
}
