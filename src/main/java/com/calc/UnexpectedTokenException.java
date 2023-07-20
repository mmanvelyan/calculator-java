package com.calc;

public class UnexpectedTokenException extends RuntimeException {
    private final int pos;
    private final String got, exp, s;
    public int getPos(){
        return pos;
    }
    public String getS(){
        return s;
    }
    public String getMessage(){
        return ("pos " + pos + " : expected " + exp + ", got '" + got + "';");
    }
    public UnexpectedTokenException(String s, int pos, String got, String exp) {
        this.s = s;
        this.pos = pos;
        this.got = got;
        this.exp = exp;
        Output.printUnexpectedToken(this);
    }
    public UnexpectedTokenException(String s, int pos, String got, String... exp) {
        String expects = "";
        for (String q : exp){
            expects = expects + "'" + q + "' or ";
        }
        expects = expects.substring(0, expects.length()-4);
        this.s = s;
        this.pos = pos;
        this.got = got;
        this.exp = expects;
        Output.printUnexpectedToken(this);
    }
}
