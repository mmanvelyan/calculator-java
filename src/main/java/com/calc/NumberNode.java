package com.calc;

public class NumberNode implements Node{

    private final double value;

    public NumberNode(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Result accept(NodeVisitor v, Variables variables, Functions functions){
        return v.accept(this, variables, functions);
    }

}
