package com.calc;

import static com.calc.CommandType.*;

public class CommandParser {

    public Command parse(String s){
        String command = s.replace(" ", "");
        if (command.equals("rpn")){
            return new Command(RPN);
        } else if (command.equals("eval")){
            return new Command(EVAL);
        } else {
            throw new UnexpectedCommandException(command);
        }
    }
}
