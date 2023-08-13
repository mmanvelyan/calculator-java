package com.calc;

import java.util.ArrayList;

public class DefineNode implements Node {

    private final Node expression;
    private final String name;
    private final ArrayList<String> argNames;

    public DefineNode(String name, ArrayList<String> argNames, Node expression){
        this.expression = expression;
        this.name = name;
        this.argNames = argNames;
    }

    public DefineNode(String name, Node expression){
        this.expression = expression;
        this.name = name;
        this.argNames = new ArrayList<>();
    }

    public Result eval(Variables variables, Functions functions) {
        if (argNames.size() == 0){
            Result result = expression.eval(variables, functions);
            variables.createVariable(name, result.getVal());
            return result;
        } else {
            Function newFun = new Function(argNames, expression);
            functions.createFunction(name, newFun);
            return new Result(0);
        }
    }

    public String rpn(Variables variables, Functions functions) {
        String res = name;
        if (argNames.size() > 0){
            res += "("+String.join(" , ", argNames)+")";
        }
        return res+" ";
    }

}
