package com.calc;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(int pos, String exp, String got) {
        super("pos " + pos + " : expected '" + exp + "', got '" + got + "';");
    }
}
