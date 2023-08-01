package com.calc;

public class CommandParser {

    public Class<? extends Command> parse(String s){
        String command = s.replace(" ", "");
        if (command.equals("rpn")){
            return ReversePolishNotation.class;
        } else if (command.equals("eval")){
            return Eval.class;
        } else {
            throw new UnexpectedCommandException(command);
        }
    }
}
