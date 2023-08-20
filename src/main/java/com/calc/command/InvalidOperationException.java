package com.calc.command;

import com.calc.lexer.Type;
import com.calc.node.Node;

public class InvalidOperationException extends RuntimeException {

    InvalidOperationException(Node node, Type operator){
        super("Invalid operation '"+operator.toString()+"' in "+node.getClass().toString());
    }

}
