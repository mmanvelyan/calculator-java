package com.calc;

public class UnexpectedTokenException extends RuntimeException {
    private final int pos;
    private final String got, exp;
    public int getPos(){
        return pos;
    }

    public String getMessage(){
        return ("pos " + pos + " : expected " + exp + ", got '" + got + "';");
    }

    public UnexpectedTokenException(Token got, String exp) {
        this.pos = got.getPos();
        this.got = got.getType().toString();
        this.exp = exp;
    }

    public UnexpectedTokenException(Token got, String... exp) {
        String expects = "";
        for (String q : exp){
            expects = expects + "'" + q + "' or ";
        }
        expects = expects.substring(0, expects.length()-4);
        this.pos = got.getPos();
        this.got = got.getType().toString();
        this.exp = expects;
    }
}

