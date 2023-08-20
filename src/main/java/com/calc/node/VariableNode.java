package com.calc.node;

import com.calc.Functions;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.Variables;

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

    public Result accept(NodeVisitor visitor, Variables variables, Functions functions){
        return visitor.accept(this, variables, functions);
    }

}
