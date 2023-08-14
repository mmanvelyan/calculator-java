package com.calc;

import java.util.List;

public class ReversePolishNotationNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Variables variables, Functions functions) {
        String leftRPN = node.getL().accept(this, variables, functions).getStr();
        String rightRPN = node.getR().accept(this, variables, functions).getStr();
        Type operator = node.getOperator();
        return new Result(leftRPN+rightRPN+operator.toString()+" ");
    }

    @Override
    public Result accept(DefineNode node, Variables variables, Functions functions) {
        String res = node.getName();
        List<String> argNames = node.getArgNames();
        if (argNames.size() > 0){
            res += "("+String.join(" , ", argNames)+")";
        }
        return new Result(res+" ");
    }

    @Override
    public Result accept(FunctionCallNode node, Variables variables, Functions functions) {
        String res = node.getName() + "(";
        List<Node> arguments = node.getArguments();
        for (Node arg : arguments){
            res += arg.accept(this, variables, functions).getStr() + ", ";
        }
        res = res.substring(0, res.length()-3) + ") ";
        return new Result(res);
    }

    @Override
    public Result accept(NumberNode node, Variables variables, Functions functions) {
        double value = node.getValue();
        return new Result(value + " ");
    }

    @Override
    public Result accept(VariableNode node, Variables variables, Functions functions) {
        String name = node.getName();
        return new Result(name+" ");
    }

}
