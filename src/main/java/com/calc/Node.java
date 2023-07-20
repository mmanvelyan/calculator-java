package com.calc;

public class Node {
    private final Node l, r;
    private final Token v;

    public Token getToken(){
        return v;
    }

    public Node getL(){
        return l;
    }

    public Node getR(){
        return r;
    }

    public Node (Token v, Node l, Node r){
        this.v = new Token(v);
        this.l = l;
        this.r = r;
    }
}

