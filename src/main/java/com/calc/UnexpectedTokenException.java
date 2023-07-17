package com.calc;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(int pos, String got, String exp) {
        super("pos " + pos + " : expected " + exp + ", got '" + got + "';");
    }
    public UnexpectedTokenException(int pos, String got, String... exp) {
        String expects = "";
        for (String s : exp){
            expects = expects + "'" + s + "' or ";
        }
        expects = expects.substring(0, expects.length()-4);

        throw new UnexpectedTokenException(pos, got, expects);
    }
}
