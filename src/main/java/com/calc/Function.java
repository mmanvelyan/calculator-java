package com.calc;

import java.util.ArrayList;

public class Function {

    private final ArrayList<Node> args;

    private final Node expression;

    public Function(ArrayList<Node> args, Node expression) {
        this.args = args;
        this.expression = expression;
    }

    public ArrayList<Node> getArgs() {
        return args;
    }

    public Node getExpression() {
        return expression;
    }

}
