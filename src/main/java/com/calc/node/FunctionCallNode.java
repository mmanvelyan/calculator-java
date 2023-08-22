package com.calc.node;

import com.calc.*;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionCallNode implements Node {
    private final List<Node> arguments;
    private final String name;
    private final int pos;

    public FunctionCallNode(List<Node> arguments, String name, int pos) {
        this.arguments = arguments;
        this.name = name;
        this.pos = pos;
    }

    public List<Node> getArguments() {
        return Collections.unmodifiableList(arguments);
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

    public String toString(){
        String res = "[pos = " + pos + ", call " + name + "(";
        List<String> argStrings = new ArrayList<>();
        for (Node arg : arguments){
            argStrings.add(arg.toString());
        }
        return res + String.join(", ", argStrings) + ")]";
    }

}
