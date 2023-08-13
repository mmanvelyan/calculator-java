package com.calc;

public class BinaryOperatorNode implements Node{
    private final Type operator;
    private final Node l;
    private final Node r;

    public BinaryOperatorNode(Type operator, Node l, Node r){
        this.operator = operator;
        this.l = l;
        this.r = r;
    }

    public Result eval(Variables variables, Functions functions){
        switch (operator) {
            case ADD:
                return new Result(l.eval(variables, functions).getVal() + r.eval(variables, functions).getVal());
            case SUB:
                return new Result(l.eval(variables, functions).getVal() - r.eval(variables, functions).getVal());
            case MUL:
                return new Result(l.eval(variables, functions).getVal() * r.eval(variables, functions).getVal());
            case DIV:
                double rEval = r.eval(variables, functions).getVal();
                if (rEval == 0) {
                    throw new ArithmeticException("/ by 0");
                }
                return new Result(l.eval(variables, functions).getVal() / rEval);
            case POWER:
                return new Result(Math.pow(l.eval(variables, functions).getVal(), r.eval(variables, functions).getVal()));
            default:
                return null;
        }
    }

    public String rpn(Variables variables, Functions functions){
        return l.rpn(variables, functions)+r.rpn(variables, functions)+operator.toString()+" ";
    }

}
