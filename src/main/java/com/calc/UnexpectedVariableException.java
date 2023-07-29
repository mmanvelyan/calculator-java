package com.calc;

public class UnexpectedVariableException extends RuntimeException implements PosisionException {
    private final int pos;
    private final String name;

    public String getMessage(){
        return ("pos " + pos + ", got unexpected variable name '" + name + "'");
    }

    public int getPos(){
        return pos;
    }

    public UnexpectedVariableException(Token var){
        this.pos = var.getPos();
        this.name = var.getName();
    }
}
