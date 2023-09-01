package com.calc.intrinsicFunction;

public class UnexpectedDerivativeException extends RuntimeException {
    public UnexpectedDerivativeException(String name) {
        super("function '"+name+"' is not differentiable");
    }
}
