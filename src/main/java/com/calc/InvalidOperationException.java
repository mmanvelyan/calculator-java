package com.calc;

public class InvalidOperationException extends RuntimeException {

    InvalidOperationException(Node node, Type operator){
        super("Invalid operation '"+operator.toString()+"' in "+node.getClass().toString());
    }

}
