package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;

public class VariableNode implements Node {
    private final String name;
    private final int pos;

    public VariableNode(String name, int pos){
        this.name = name;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public int getPos() {
        return pos;
    }

    public Result accept(NodeVisitor visitor, Context context){
        return visitor.accept(this, context);
    }

    public String toString(){
        return "[pos " + pos + ", variable " + name + "]";
    }

}
