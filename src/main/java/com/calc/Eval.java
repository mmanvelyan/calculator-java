package com.calc;

import static com.calc.Type.ADD;

public class Eval{
    public float eval(Node n, Variables variables){
        Token v = n.v;
        Node l = n.l;
        Node r = n.r;
        if (v.tp == Type.EQ){
            variables.createVariable(l.v.name, eval(r, variables));
            System.out.println("Variable "+l.v.name+" is set to "+ variables.getValue(l.v.name));
            return 0;
        }
        if (v.tp == Type.VAR){
            return variables.getValue(v.name);
        }
        if (v.tp == Type.NUM){
            return v.val;
        }
        if (v.tp == ADD){
            return eval(l, variables)+eval(r, variables);
        }
        if (v.tp == Type.SUB){
            return eval(l, variables)-eval(r, variables);
        }
        if (v.tp == Type.MUL){
            return eval(l, variables)*eval(r, variables);
        }
        if (v.tp == Type.DIV) {
            float rEval = eval(r, variables);
            if (rEval == 0) {
                throw new ArithmeticException("/ by 0");
            }
            return eval(l, variables) / rEval;
        }
        return 0;
    }
}
