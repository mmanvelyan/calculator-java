package com.calc;

import static com.calc.Type.ADD;

public class Eval {
    public EvalResult eval(Node n, Variables variables) {
        Token token = n.getToken();
        Node l = n.getL();
        Node r = n.getR();
        switch (token.getType()) {
            case ASS:
                float value = eval(r, variables).getRes();
                variables.createVariable(l.getToken().getName(), value);
                return new EvalResult(value);
            case VAR:
                if (variables.getValue(token.getName()) == null) {
                    throw new UnexpectedVariableException(token);
                }
                return new EvalResult(variables.getValue(token.getName()));
            case NUM:
                return new EvalResult(token.getVal());
            case ADD:
                return new EvalResult(eval(l, variables).getRes() + eval(r, variables).getRes());
            case SUB:
                return new EvalResult(eval(l, variables).getRes() - eval(r, variables).getRes());
            case MUL:
                return new EvalResult(eval(l, variables).getRes() * eval(r, variables).getRes());
            case DIV:
                float rEval = eval(r, variables).getRes();
                if (rEval == 0) {
                    throw new ArithmeticException("/ by 0");
                }
                return new EvalResult(eval(l, variables).getRes() / rEval);
            default:
                return null;
        }
    }
}

