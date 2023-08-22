package com.calc.command;

import com.calc.Functions;
import com.calc.Variables;
import com.calc.node.Node;
import com.calc.node.NumberNode;

import static com.calc.command.ResultType.STR;
import static com.calc.command.ResultType.VAL;

public class Result {
    private final double val;
    private final String str;
    private final Node expression;
    private final ResultType type;

    public Result(ResultType type, double f, String s, Node expression){
        this.type = type;
        this.val = f;
        this.str = s;
        this.expression = expression;
    }

    public Result(double f){
        this(VAL, f, null, new NumberNode(f));
    }

    public Result(String s){
        this(ResultType.STR, 0, s, null);
    }

    public Result(Node expression){
        this(ResultType.EXP, 0, null, expression);
    }

    public ResultType getType(){
        return type;
    }

    public double getVal(){
        return val;
    }

    public String getStr(){
        return str;
    }

    public Node getExpression() {
        return expression;
    }

    public String toString(){
        String res = type.toString() + " [";
        if (type == VAL){
            res += val;
        } else if (type == STR){
            res += str;
        } else {
            res += expression.accept(new PrintNodeVisitor(), new Variables(), new Functions());
        }
        res += "]";
        return res;
    }
}
