package com.calc;

import static com.calc.Type.*;

public class BaseLexer implements Lexer {
    private final String s;
    private int pos;

    public BaseLexer(String s){
        this.s = s;
        this.pos = 0;
    }

    public Token nextToken(){
        Token nxt;
        if (pos >= s.length()){
            return new Token(Type.END, pos);
        }
        char c = s.charAt(pos);
        pos++;
        while (c == ' '){
            if (pos >= s.length()){
                return new Token(Type.END, pos);
            }
            c = s.charAt(pos);
            pos++;
        }
        switch (c){
            case '=':
                nxt = new Token(Type.ASS, pos-1);
                break;
            case '(':
                nxt = new Token(Type.OPEN_BR, pos-1);
                break;
            case ')':
                nxt = new Token(Type.CLOSING_BR, pos-1);
                break;
            case '+':
                nxt = new Token(ADD, pos-1);
                break;
            case '-':
                nxt = new Token(Type.SUB, pos-1);
                break;
            case '*':
                nxt = new Token(Type.MUL, pos-1);
                break;
            case  '/':
                nxt = new Token(Type.DIV, pos-1);
                break;
            case ',':
                nxt = new Token(COMMA, pos-1);
                break;
            default:
                if (Character.isDigit(c)){
                    pos--;
                    float val = getNumericValue();
                    nxt = new Token(val, pos-1);
                } else if (Character.isLetter(c)) {
                    pos--;
                    String name = getVariableName();
                    nxt = new Token(name, pos-1);
                } else {
                    throw new UnexpectedTokenException(new Token(INV, pos-1), "expression");
                }
        }
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

