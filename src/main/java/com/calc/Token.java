package com.calc;

public class Token {
    public Type tp;
    public String name;
    public float val;
    Token () {}
    Token (Token t){
        this.tp = t.tp;
        this.val = t.val;
        this.name = t.name;
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
    /*
    Token (String name, float val){
        this.tp = Type.VAR;
        this.val = val;
        this.name = name;
    }
    */
}





