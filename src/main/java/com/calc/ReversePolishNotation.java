package com.calc;

public class ReversePolishNotation {

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

    public Result reversePolishNotation(Node expression){
        getRPN(expression);
        return new Result(result);
    }
}
