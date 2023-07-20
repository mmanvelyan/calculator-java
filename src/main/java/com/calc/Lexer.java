package com.calc;

import static com.calc.Type.*;

public class Lexer {
    private final String s;
    private int pos, prevPos;

    public Lexer(Lexer lex){
        this.s = lex.s;
        this.pos = lex.pos;
        this.prevPos = lex.prevPos;
    }

    public Lexer(String s, int pos){
        this.s = s;
        this.pos = pos;
    }
    public String getS(){
        return s;
    }

    public int getPos(){
        return pos;
    }
    public void setPos(int pos){
        this.prevPos = this.pos;
        this.pos = pos;
    }

    public void prevToken(){
        pos = prevPos;
    }
    public Token nextToken(){
        if (pos >= s.length()){
            return new Token(Type.END);
        }
        char c = s.charAt(pos);
        prevPos = pos;
        pos++;
        while (c == ' '){
            c = s.charAt(pos);
            prevPos++;
            pos++;
        }
        if (c == '='){
            return new Token(Type.ASS);
        } else if (c == '('){
            return new Token(Type.OPEN_BR);
        } else if (c == ')'){
            return new Token(Type.CLOSING_BR);
        } else if (c == '+'){
            return new Token(ADD);
        } else if (c == '-'){
            return new Token(Type.SUB);
        } else if (c == '*'){
            return new Token(Type.MUL);
        } else if (c == '/'){
            return new Token(Type.DIV);
        } else if (Character.isDigit(c)){
            pos--;
            float val = getNumericValue();
            return new Token(val);
        } else if (Character.isLetter(c)) {
            pos--;
            String name = getVariableName();
            return new Token(name);
        } else {
            throw new UnexpectedTokenException(s, pos, s.substring(pos, pos+1), "expression");
        }
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
