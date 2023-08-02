package com.calc;

public class ReversePolishNotation extends Command{

    private String result = "";

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

    public Result execute(Node expression, Variables variables){
        getRPN(expression);
        return new Result(result);
    }
}
