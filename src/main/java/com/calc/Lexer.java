package com.calc;

import static com.calc.Type.*;

public class Lexer {
    private final String s;
    private int pos;
    private final LexerPrevTokens prevTokens;

    public Lexer(Lexer lex){
        this.s = lex.s;
        this.pos = lex.pos;
        this.prevTokens = new LexerPrevTokens(lex.prevTokens);
    }

    public Lexer(String s, int pos){
        this.s = s;
        this.pos = pos;
        this.prevTokens = new LexerPrevTokens(pos);
    }
    public String getS(){
        return s;
    }

    public int getPos(){
        return pos;
    }

    public void returnToPrevPos(){
        pos = prevTokens.returnToPrevPos();
    }

    public Token nextToken(){
        Token nxt;
        if (pos >= s.length()){
            nxt = new Token(Type.END);
            prevTokens.addToken(nxt, pos);
            return nxt;
        }
        char c = s.charAt(pos);
        pos++;
        while (c == ' '){
            if (pos >= s.length()){
                nxt = new Token(Type.END);
                prevTokens.addToken(nxt, pos);
                return nxt;
            }
            c = s.charAt(pos);
            pos++;
        }
        if (c == '='){
            nxt = new Token(Type.ASS);
        } else if (c == '('){
            nxt = new Token(Type.OPEN_BR);
        } else if (c == ')'){
            nxt = new Token(Type.CLOSING_BR);
        } else if (c == '+'){
            nxt = new Token(ADD);
        } else if (c == '-'){
            nxt = new Token(Type.SUB);
        } else if (c == '*'){
            nxt = new Token(Type.MUL);
        } else if (c == '/'){
            nxt = new Token(Type.DIV);
        } else if (Character.isDigit(c)){
            pos--;
            float val = getNumericValue();
            nxt = new Token(val);
        } else if (Character.isLetter(c)) {
            pos--;
            String name = getVariableName();
            nxt = new Token(name);
        } else {
            throw new UnexpectedTokenException(s, pos, s.substring(pos, pos+1), "expression");
        }
        prevTokens.addToken(nxt, pos);
        return nxt;
    }

    private String getVariableName() {
        int begPos = pos, len = s.length();
        while (pos < len && (Character.isLetter(s.charAt(pos)) || Character.isDigit(s.charAt(pos)))){
            pos++;
        }
        return s.substring(begPos, pos);
    }
    private float getNumericValue() {
        int begPos = pos, len = s.length();
        while (pos < len && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')){
            pos++;
        }
        return Float.parseFloat(s.substring(begPos, pos));
    }
}

