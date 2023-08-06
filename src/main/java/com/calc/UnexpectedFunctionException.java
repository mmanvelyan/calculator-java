package com.calc;

public class UnexpectedFunctionException extends RuntimeException implements PositionException{

    private final int pos;

    private final String name;

    public String getMessage(){
        return ("pos " + pos + ", got unexpected function name '" + name + "'");
    }

    public int getPos(){
        return pos;
    }

    public UnexpectedFunctionException(Token var){
        this.pos = var.getPos();
        this.name = var.getName();
    }
}
