package com.calc;

public class NumberNode implements Node{

    private final double value;

    public NumberNode(double value){
        this.value = value;
    }

    public Result eval(Variables variables, Functions functions){
        return new Result(value);
    }

    public String rpn(Variables variables, Functions functions){
        return value + " ";
    }

}
