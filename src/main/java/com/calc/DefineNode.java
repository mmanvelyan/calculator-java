package com.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefineNode implements Node {

    private final Node expression;
    private final String name;
    private final List<String> argNames;

    public DefineNode(String name, List<String> argNames, Node expression){
        this.expression = expression;
        this.name = name;
        this.argNames = argNames;
    }

    public DefineNode(String name, Node expression){
        this.expression = expression;
        this.name = name;
        this.argNames = new ArrayList<>();
    }

    public Node getExpression() {
        return expression;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgNames() {
        return Collections.unmodifiableList(argNames);
    }

    public Result accept(NodeVisitor visitor, Variables variables, Functions functions){
        return visitor.accept(this, variables, functions);
    }

}
