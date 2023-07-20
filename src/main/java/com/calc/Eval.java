package com.calc;

import static com.calc.Type.ADD;

public class Eval{
    public EvalResult eval(Node n, Variables variables){
        Token v = n.getToken();
        Node l = n.getL();
        Node r = n.getR();
        if (v.getTp() == Type.ASS){
            variables.createVariable(l.getToken().getName(), eval(r, variables).getRes());
            return new EvalResult("Variable "+l.getToken().getName()+" is set to "+ variables.getValue(l.getToken().getName()));
        }
        if (v.getTp() == Type.VAR){
            return new EvalResult(variables.getValue(v.getName()));
        }
        if (v.getTp() == Type.NUM){
            return new EvalResult(v.getVal());
        }
        if (v.getTp() == ADD){
            return new EvalResult(eval(l, variables).getRes()+eval(r, variables).getRes());
        }
        if (v.getTp() == Type.SUB){
            return new EvalResult(eval(l, variables).getRes()-eval(r, variables).getRes());
        }
        if (v.getTp() == Type.MUL){
            return new EvalResult(eval(l, variables).getRes()*eval(r, variables).getRes());
        }
        if (v.getTp() == Type.DIV) {
            float rEval = eval(r, variables).getRes();
            if (rEval == 0) {
                throw new ArithmeticException("/ by 0");
            }
            return new EvalResult(eval(l, variables).getRes() / rEval);
        }
        return null;
    }
}

