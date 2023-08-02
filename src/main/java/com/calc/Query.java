package com.calc;

public class Query {

    private final Command command;
    private final Node expression;

    public Query(Command command, Node expression){
        this.command = command;
        this.expression = expression;
    }

    public Command getCommand() {
        return command;
    }
    public Node getExpression() {
        return expression;
    }

}
