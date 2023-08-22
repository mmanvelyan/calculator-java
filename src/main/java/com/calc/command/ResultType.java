package com.calc.command;

public enum ResultType {
    STR("string"),
    VAL("value"),
    EXP("expression");

    private final String description;
    ResultType(String description){
        this.description = description;
    }
    public String toString(){
        return description;
    }
}
