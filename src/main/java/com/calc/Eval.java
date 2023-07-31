package com.calc;

public class Eval {
    public Result eval(Node n, Variables variables) {
        Token token = n.getToken();
        Node l = n.getL();
        Node r = n.getR();
        switch (token.getType()) {
            case ASS:
                float value = eval(r, variables).getVal();
                variables.createVariable(l.getToken().getName(), value);
                return new Result(value);
            case VAR:
                if (variables.getValue(token.getName()) == null) {
                    throw new UnexpectedVariableException(token);
                }
                return new Result(variables.getValue(token.getName()));
            case NUM:
                return new Result(token.getVal());
            case ADD:
                return new Result(eval(l, variables).getVal() + eval(r, variables).getVal());
            case SUB:
                return new Result(eval(l, variables).getVal() - eval(r, variables).getVal());
            case MUL:
                return new Result(eval(l, variables).getVal() * eval(r, variables).getVal());
            case DIV:
                float rEval = eval(r, variables).getVal();
                if (rEval == 0) {
                    throw new ArithmeticException("/ by 0");
                }
                return new Result(eval(l, variables).getVal() / rEval);
            default:
                return null;
        }
    }
}

