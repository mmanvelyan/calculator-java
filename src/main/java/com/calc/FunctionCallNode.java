package com.calc;

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

}
