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
    public UnexpectedTokenException(int pos, String got, String exp) {
        this.pos = pos;
        this.got = got;
        this.exp = exp;
        for (int i = 0; i < pos; i++) {
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println(getMessage());
    }
    public UnexpectedTokenException(int pos, String got, String... exp) {
        String expects = "";
        for (String q : exp){
            expects = expects + "'" + q + "' or ";
        }
        expects = expects.substring(0, expects.length()-4);
        this.pos = pos;
        this.got = got;
        this.exp = expects;
        for (int i = 0; i < pos; i++) {
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println(getMessage());
    }
}
