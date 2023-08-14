package com.calc;

public class Query {

    private final NodeVisitor command;
    private final Node expression;

    public Query(NodeVisitor command, Node expression){
        this.command = command;
        this.expression = expression;
    }

    public NodeVisitor getCommand() {
        return command;
    }

    public Node getExpression() {
        return expression;
    }

}
