package com.calc.commands;

import com.calc.lexer.Type;
import com.calc.nodes.Node;

public class InvalidOperationException extends RuntimeException {

    InvalidOperationException(Node node, Type operator){
        super("Invalid operation '"+operator.toString()+"' in "+node.getClass().toString());
    }

}
