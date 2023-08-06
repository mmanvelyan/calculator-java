package com.calc;

import java.util.ArrayList;

public class Token {
    private final Type type;
    private final String name;
    private final int pos;
    private final float val;
    private final ArrayList<Node> args;

    Token (Type type, String name, int pos, float val, ArrayList<Node> args){
        this.type = type;
        this.name = name;
        this.pos = pos;
        this.val = val;
        this.args = new ArrayList<>(args);
    }
    Token (Token t){
        this(t.type, t.name, t.pos, t.val, t.args);
    }

    Token (String s, int pos){
        this(Type.VAR, s, pos, 0, new ArrayList<>());
    }

    Token (Type type) {
        this(type, "", 0, 0, new ArrayList<>());
    }

    Token (Type type, int pos) {
        this(type, "", pos, 0, new ArrayList<>());
    }

    Token (float val){
        this(Type.NUM, "", 0, val, new ArrayList<>());
    }

    Token (float val, int pos){
        this(Type.NUM, "", pos, val, new ArrayList<>());
    }

    Token (String name, ArrayList<Node> args, int pos){
        this(Type.FUN, name, pos, 0, args);
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

    public ArrayList<Node> getArgs() {
        return args;
    }


    public String toString() {
        if (type == Type.NUM) {
            return String.valueOf(val);
        } else if (type == Type.VAR) {
            return name;
        } else if (type == Type.FUN){
            return name;
        } else {
            return type.toString();
        }
    }
}





