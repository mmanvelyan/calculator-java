package com.calc.command;

import com.calc.Context;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.List;

public class ReversePolishNotationNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        Node l = node.getL();
        String leftRPN = l.accept(this, context).getStr();
        Node r = node.getR();
        String rightRPN = r.accept(this, context).getStr();
        Type operator = node.getOperator();
        return new Result(leftRPN+rightRPN+operator.toString()+" ");
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        String res = node.getName();
        List<String> argNames = node.getArgNames();
        if (argNames.size() > 0){
            res += "("+String.join(" , ", argNames)+")";
        }
        return new Result(res+" ");
    }

    @Override
    public Result accept(FunctionCallNode node, Context context) {
        String res = node.getName() + "(";
        List<Node> arguments = node.getArguments();
        for (Node arg : arguments){
            res += arg.accept(this, context).getStr() + ", ";
        }
        res = res.substring(0, res.length()-3) + ") ";
        return new Result(res);
    }

    @Override
    public Result accept(NumberNode node, Context context) {
        double value = node.getValue();
        return new Result(value + " ");
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        String name = node.getName();
        return new Result(name+" ");
    }

}
