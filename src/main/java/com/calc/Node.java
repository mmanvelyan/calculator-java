package com.calc;

import static com.calc.Type.ADD;

public class Node {
    private Node l, r;
    private Token v;

    public float eval(){
        //System.out.println(tr.tp+" "+tr.val);
        if (v.tp == Type.NUM){
            return v.val;
        }
        if (v.tp == ADD){
            return l.eval()+r.eval();
        }
        if (v.tp == Type.SUB){
            return l.eval()-r.eval();
        }
        if (v.tp == Type.MUL){
            return l.eval()*r.eval();
        }
        if (v.tp == Type.DIV) {
            float rEval = r.eval();
            if (rEval == 0) {
                throw new ArithmeticException("/ by 0");
            }
            return l.eval() / rEval;
        }
        return 0;
    }
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
