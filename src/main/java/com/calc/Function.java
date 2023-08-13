package com.calc;

import java.util.ArrayList;

public class Function {

    private final ArrayList<String> args;

    private final Node expression;

    public Function(ArrayList<String> args, Node expression) {
        this.args = args;
        this.expression = expression;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public Node getExpression() {
        return expression;
    }

}
