package com.calc.lexer;

public class RollbackLevelException extends RuntimeException {
    public RollbackLevelException(String message) {
        super(message);
    }
}
