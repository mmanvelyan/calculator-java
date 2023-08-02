package com.calc;

public class CommandParser {

    public Command parse(String s){
        String command = s.replace(" ", "");
        if (command.equals("rpn")){
            return new ReversePolishNotation();
        } else if (command.equals("eval")){
            return new Eval();
        } else {
            throw new UnexpectedCommandException(command);
        }
    }
}
