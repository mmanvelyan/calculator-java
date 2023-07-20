package com.calc;


public enum Type {
    NUM("number"),
    VAR("variable"),
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    OPEN_BR("("),
    CLOSING_BR(")"),
    END("END"),
    ASS("=");

    final private String description;
    Type(String description){
        this.description = description;
    }
    public String toString() {
        return description;
    }
}
