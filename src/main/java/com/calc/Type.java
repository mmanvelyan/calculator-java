package com.calc;


public enum Type {
    NUM,
    ADD,
    SUB,
    MUL,
    DIV,
    OPEN_BR,
    CLOSING_BR,
    END;

    public String toString(Type t) {
        if (t == NUM){
            return "number";
        } else if (t == ADD){
            return "+";
        } else if (t == SUB){
            return "-";
        } else if (t == MUL){
            return "*";
        } else if (t == DIV){
            return "/";
        } else if (t == OPEN_BR){
            return "(";
        } else if (t == CLOSING_BR){
            return ")";
        } else if (t == END){
            return "END_OF_LINE";
        }
        return null;
    }
}
