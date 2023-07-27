package com.calc;

public class Token {
    private final Type type;
    private final String name;
    private final int pos;
    private final float val;

    Token (Type type, String name, int pos, float val){
        this.type = type;
        this.name = name;
        this.pos = pos;
        this.val = val;
    }
    Token (Token t){
        this(t.type, t.name, t.pos, t.val);
    }

    Token (String s, int pos){
        this(Type.VAR, s, pos, 0);
    }

    Token (Type type) {
        this(type, "", 0, 0);
    }

    Token (Type type, int pos) {
        this(type, "", pos, 0);
    }

    Token (float val){
        this(Type.NUM, "", 0, val);
    }

    Token (float val, int pos){
        this(Type.NUM, "", pos, val);
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

    public int getPos(){
        return pos;
    }
}





