package com.calc.parser;

import com.calc.commands.EvalNodeVisitor;
import com.calc.commands.NodeVisitor;
import com.calc.commands.ReversePolishNotationNodeVisitor;

public class CommandParser {

    public NodeVisitor parse(String s){
        String command = s.replace(" ", "");
        if (command.equals("rpn")){
            return new ReversePolishNotationNodeVisitor();
        } else if (command.equals("eval")){
            return new EvalNodeVisitor();
        } else {
            throw new UnexpectedCommandException(command);
        }
    }

}
