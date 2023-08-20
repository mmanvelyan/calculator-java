package com.calc.command;

import com.calc.PositionException;

public class FunctionCycleException extends RuntimeException implements PositionException {

    private final int pos;

    private final String name;

    public String getName() {
        return name;
    }

    public String getMessage(){
        return ("pos " + pos + ", function cycle");
    }

    public int getPos(){
        return pos;
    }

    public FunctionCycleException(UnexpectedFunctionException ex) {
        pos = ex.getPos();
        name = ex.getName();
    }
}
