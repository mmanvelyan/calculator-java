package com.calc.parser;

public class UnexpectedCommandException extends RuntimeException {

    private final String command;

    public String getMessage(){
        return ("Unexpected command '" + command + "'");
    }

    public UnexpectedCommandException(String command) {
        this.command = command;
    }
}
