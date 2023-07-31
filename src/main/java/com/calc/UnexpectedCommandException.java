package com.calc;

public class UnexpectedCommandException extends RuntimeException {

    private String command;

    public String getMessage(){
        return ("Unexpected command '" + command + "'");
    }

    public UnexpectedCommandException(String command) {
        this.command = command;
    }
}
