package com.calc;

import static com.calc.Type.*;

public class Lexer {
    String s;
    int pos;
    int prevPos;
    /*
    public Lexer(String s){
        this.s = s;
    }
    */

    public Lexer(String s, int pos){
        this.s = s;
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
        if (c == '('){
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
        } else if (c <= '9' && c >= '0'){
            pos--;
            float val = getNumericValue();
            return new Token(val);
        } else {
            throw new UnexpectedTokenException(pos, s.substring(pos, pos+1), "expression");
        }
    }

    private float getNumericValue() {
        String numStr = "";
        //System.out.println(s + " " + pos.intValue());
        while (pos < s.length() && ((s.charAt(pos) <= '9' && s.charAt(pos) >= '0') || s.charAt(pos) == '.')){
            numStr += s.charAt(pos);
            pos++;
        }
        return Float.parseFloat(numStr);
    }
}
