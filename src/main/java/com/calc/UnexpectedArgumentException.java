package com.calc;

public class UnexpectedArgumentException extends RuntimeException implements PositionException{
    private final int pos;

    public String getMessage(){
        return ("pos " + pos + ", got unexpected function argument");
    }

    public int getPos(){
        return pos;
    }

    public UnexpectedArgumentException(Token var){
        this.pos = var.getPos();
    }
}
