package com.calc.command;

import com.calc.PositionException;

public class UnexpectedVariableException extends RuntimeException implements PositionException {
    private final int pos;
    private final String name;

    public String getMessage(){
        return ("pos " + pos + ", got unexpected variable name '" + name + "'");
    }

    public String getName(){
        return name;
    }

    public int getPos(){
        return pos;
    }

    public UnexpectedVariableException(String name, int pos){
        this.pos = pos;
        this.name = name;
    }
}
