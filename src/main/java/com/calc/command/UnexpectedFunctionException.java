package com.calc.command;

import com.calc.PositionException;

public class UnexpectedFunctionException extends RuntimeException implements PositionException {

    private final int pos;

    private final String name;

    public String getName() {
        return name;
    }

    public String getMessage(){
        return ("pos " + pos + ", got unexpected function name '" + name + "'");
    }

    public int getPos(){
        return pos;
    }

    public UnexpectedFunctionException(String name, int pos){
        this.pos = pos;
        this.name = name;
    }
}
