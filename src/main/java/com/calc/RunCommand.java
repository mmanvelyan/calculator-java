package com.calc;

public class RunCommand {

    public Result runCommand(Command command, Node expression, Variables variables){
        if (command.getType() == CommandType.EVAL){
            Eval e = new Eval();
            return e.eval(expression, variables);
        } else {
            ReversePolishNotation rpn = new ReversePolishNotation();
            return rpn.reversePolishNotation(expression);
        }
    }

}
