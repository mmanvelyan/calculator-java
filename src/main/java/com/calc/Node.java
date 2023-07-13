package com.calc;

import static com.calc.Type.ADD;
import static com.calc.Type.VAR;

public class Node {
    private Node l, r;
    private Token v;

    private Variables variables;

    public float eval(Variables variables){
        //System.out.println(tr.tp+" "+tr.val);
        if (v.tp == Type.VAR){
            return variables.getValue(v.name);
        }
        if (v.tp == Type.NUM){
            return v.val;
        }
        if (v.tp == ADD){
            return l.eval(variables)+r.eval(variables);
        }
        if (v.tp == Type.SUB){
            return l.eval(variables)-r.eval(variables);
        }
        if (v.tp == Type.MUL){
            return l.eval(variables)*r.eval(variables);
        }
        if (v.tp == Type.DIV) {
            float rEval = r.eval(variables);
            if (rEval == 0) {
                throw new ArithmeticException("/ by 0");
            }
            return l.eval(variables) / rEval;
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
