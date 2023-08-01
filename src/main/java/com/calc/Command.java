package com.calc;

public abstract class Command {

    protected final Node expression;

    public Command(Node expression){
        this.expression = expression;
    }

    public abstract Result execute(Variables variables);

}
