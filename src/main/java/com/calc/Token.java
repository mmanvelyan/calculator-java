package com.calc;

public class Token {
    private final Type type;
    private final String name;
    private final int pos;
    private final double val;

    Token (Type type, String name, int pos, double val){
        this.type = type;
        this.name = name;
        this.pos = pos;
        this.val = val;
    }

    Token (String s, int pos){
        this(Type.NAME, s, pos, 0);
    }

    Token (Type type, int pos) {
        this(type, "", pos, 0);
    }

    Token (double val, int pos){
        this(Type.NUM, "", pos, val);
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getVal() {
        return val;
    }

    public int getPos(){
        return pos;
    }


    public String toString() {
        if (type == Type.NUM) {
            return String.valueOf(val);
        } else if (type == Type.NAME) {
            return name;
        } else {
            return type.toString();
        }
    }
}





