package com.calc;

public class Token {
    Type tp;
    float val;
    Token () {}
    Token (Token t){
        this.tp = t.tp;
        this.val = t.val;
    }
    Token (Type tp) {
        this.tp = tp;
    }
    Token (Type tp, float val){
        this.tp = tp;
        this.val = val;
    }
}
