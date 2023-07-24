package com.calc;

public class Token {
    private final Type type;
    private String name;
    private float val;
    Token (Token t){
        this.type = t.type;
        this.val = t.val;
        this.name = t.name;
    }
    Token (String s){
        this.type = Type.VAR;
        this.name = s;
    }
    Token (Type type) {
        this.type = type;
    }
    Token (Type type, float val){
        this.type = type;
        this.val = val;
    }
    Token (float val){
        this.type = Type.NUM;
        this.val = val;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float getVal() {
        return val;
    }
}





