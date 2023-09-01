package com.calc.command;

public class IntrinsicFunctionDefinitionException extends RuntimeException {
    public IntrinsicFunctionDefinitionException(String name) {
        super("error: redefinition of intrinsic function '"+name+"'");
    }
}
