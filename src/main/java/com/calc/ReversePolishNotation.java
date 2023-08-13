package com.calc;

public class ReversePolishNotation extends Command{

    public Result execute(Node expression, Variables variables, Functions functions){
        return new Result(expression.rpn(variables, functions));
    }

}
