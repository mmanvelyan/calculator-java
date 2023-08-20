package com.calc.parser;

import com.calc.command.EvalNodeVisitor;
import com.calc.command.EvalStrictNodeVisitor;
import com.calc.command.NodeVisitor;
import com.calc.command.ReversePolishNotationNodeVisitor;

public class CommandParser {

    public NodeVisitor parse(String s){
        String command = s.replace(" ", "");
        if (command.equals("rpn")){
            return new ReversePolishNotationNodeVisitor();
        } else if (command.equals("evalstrict")){
            return new EvalStrictNodeVisitor();
        } else if (command.equals("eval")){
            return new EvalNodeVisitor();
        } else {
            throw new UnexpectedCommandException(command);
        }
    }

}
