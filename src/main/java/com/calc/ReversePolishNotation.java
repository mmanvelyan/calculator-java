package com.calc;

public class ReversePolishNotation extends Command{

    private String result = "";

    public ReversePolishNotation(Node expression){
        super(expression);
    }

    public void getRPN(Node n){
        Node l = n.getL(), r = n.getR();
        if (l != null) {
            getRPN(l);
        }
        if (r != null){
            getRPN(r);
        }
        result += n.getToken().toString() + " ";
    }

    public Result execute(Variables variables){
        getRPN(expression);
        return new Result(result);
    }
}
