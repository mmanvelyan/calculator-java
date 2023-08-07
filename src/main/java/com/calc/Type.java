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
    ASS("="),
    INV("invalid symbol"),
    FUN("function"),
    COMMA(","),
    POWER("^"),

    SQRT("sqrt"),
    EXP("exp"),
    LN("ln"),
    LG("lg"),
    LOG("log"),
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    ARCSIN("arcsin"),
    ARCCOS("arccos"),
    ARCTAN("arctan"),
    ABS("abs"),
    MOD("mod");

    final private String description;
    Type(String description){
        this.description = description;
    }
    public String toString() {
        return description;
    }
}
