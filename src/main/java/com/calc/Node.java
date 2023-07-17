package com.calc;

import static com.calc.Type.ADD;
import static com.calc.Type.VAR;

public class Node {
    public Node l, r;
    public Token v;

    public void print(){
        System.out.println(v.tp.toString() + ", val = " + v.val);
        if (l != null) {
            l.print();
        }
        if (r != null){
            r.print();
        }
    }

    public Type getType(){
        return v.tp;
    }

    public float getValue(){
        return v.val;
    }

    public Node (Token v, Node l, Node r){
        this.v = new Token(v);
        this.l = l;
        this.r = r;
    }
}

