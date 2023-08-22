package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;

public class NumberNode implements Node {

    private final double value;

    public NumberNode(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Result accept(NodeVisitor v, Context context){
        return v.accept(this, context);
    }

    public String toString(){
        return "[" + value + "]";
    }
}
