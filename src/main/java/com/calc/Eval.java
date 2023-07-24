package com.calc;

import static com.calc.Type.ADD;

public class Eval{
    public EvalResult eval(Node n, Variables variables){
        Token v = n.getToken();
        Node l = n.getL();
        Node r = n.getR();
        if (v.getType() == Type.ASS){
            float value = eval(r, variables).getRes();
            variables.createVariable(l.getToken().getName(), value);
            return new EvalResult(value);
        }
        if (v.getType() == Type.VAR){
            if (variables.getValue(v.getName()) == null){
                return null;
            }
            return new EvalResult(variables.getValue(v.getName()));
        }
        if (v.getType() == Type.NUM){
            return new EvalResult(v.getVal());
        }
        if (v.getType() == ADD){
            return new EvalResult(eval(l, variables).getRes()+eval(r, variables).getRes());
        }
        if (v.getType() == Type.SUB){
            return new EvalResult(eval(l, variables).getRes()-eval(r, variables).getRes());
        }
        if (v.getType() == Type.MUL){
            return new EvalResult(eval(l, variables).getRes()*eval(r, variables).getRes());
        }
        if (v.getType() == Type.DIV) {
            float rEval = eval(r, variables).getRes();
            if (rEval == 0) {
                throw new ArithmeticException("/ by 0");
            }
            return new EvalResult(eval(l, variables).getRes() / rEval);
        }
        return null;
    }
}

