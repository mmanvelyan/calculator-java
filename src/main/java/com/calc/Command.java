package com.calc;

public abstract class Command {

    public abstract Result execute(Node expression, Variables variables);

}
