package com.calc;

public class Eval extends Command {

    public Result execute(Node expression, Variables variables, Functions functions){
        return expression.eval(variables, functions);
    }

}

