package com.calc;

import javax.management.RuntimeErrorException;

public class BracketsException extends RuntimeException {
    public BracketsException(String errorMessage) {
        super(errorMessage, new RuntimeException());
    }
}
