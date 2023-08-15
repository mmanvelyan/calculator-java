package com.calc.lexer;


public enum Type {
    NUM("number"),
    NAME("name"),
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    OPEN_BR("("),
    CLOSING_BR(")"),
    END("END"),
    ASS("="),
    INV("invalid symbol"),
    COMMA(","),
    POWER("^");

    final private String description;
    Type(String description){
        this.description = description;
    }
    public String toString() {
        return description;
    }
}
