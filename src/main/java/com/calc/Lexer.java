package com.calc;

import static com.calc.Type.*;

public class Lexer {
    String s;
    int pos;
    int prevPos;

    public Lexer(Lexer lex){
        this.s = lex.s;
        this.pos = lex.pos;
    }

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
        if (c == ' '){
            return nextToken();
        } else if (c == '='){
            return new Token(Type.EQ);
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
        } else if (c <= '9' && c >= '0'){
            pos--;
            float val = getNumericValue();
            return new Token(val);
        } else if (Character.isLetter(c)) {
            pos--;
            String name = getVariableName();
            return new Token(name);
        } else {
            throw new UnexpectedTokenException(pos, s.substring(pos, pos+1), "expression");
        }
    }

    private void checkName(String s){
        if (s.length() == 0){
            throw new UnexpectedTokenException(0, "=", "variable name");
        }
        if (!Character.isLetter(s.charAt(0))){
            throw new UnexpectedTokenException(0, s.substring(0, 1), "letter");
        }
        for (int i = 1; i < s.length(); i++){
            if (!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))){
                throw new UnexpectedTokenException(i, s.substring(i, i+1), "letter or digit");
            }
        }
    }
    private String getVariableName() {
        String name = "";
        while (pos < s.length() && (Character.isLetter(s.charAt(pos)) || Character.isDigit(s.charAt(pos)))){
            name += s.charAt(pos);
            pos++;
        }
        checkName(name);
        return name;
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
