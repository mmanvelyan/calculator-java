package com.calc;

public class ReversePolishNotation extends Command{

    private String result = "";

    public void getRPN(Node n){
        if (n.getToken().getType() == Type.FUN){
            result += n.getToken().toString() + "(";
            for (Node arg : n.getToken().getArgs()){
                getRPN(arg);
                result += ", ";
            }
            result = result.substring(0, result.length()-3);
            result += ")";
            return;
        }
        Node l = n.getL(), r = n.getR();
        if (l != null) {
            getRPN(l);
        }
        if (r != null){
            getRPN(r);
        }
        result += n.getToken().toString() + " ";
    }

    public Result execute(Node expression, Variables variables, Functions functions){
        getRPN(expression);
        return new Result(result);
    }
}
