package com.calc;

public class Token {
    private final Type tp;
    private String name;
    private float val;
    Token (Token t){
        this.tp = t.tp;
        this.val = t.val;
        this.name = t.name;
    }
    Token (String s){
        this.tp = Type.VAR;
        this.name = s;
    }
    Token (Type tp) {
        this.tp = tp;
    }
    Token (Type tp, float val){
        this.tp = tp;
        this.val = val;
    }
    Token (float val){
        this.tp = Type.NUM;
        this.val = val;
    }

    public Type getTp() {
        return tp;
    }

    public String getName() {
        return name;
    }

    public float getVal() {
        return val;
    }
}





