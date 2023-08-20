package com.calc.command;

import com.calc.Functions;
import com.calc.Variables;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.List;

public class PrintNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Variables variables, Functions functions) {
        String leftNode = node.getL().accept(this, variables, functions).getStr();
        String rightNode = node.getR().accept(this, variables, functions).getStr();
        Type operator = node.getOperator();
        return new Result(leftNode+operator.toString()+rightNode);
    }

    @Override
    public Result accept(DefineNode node, Variables variables, Functions functions) {
        String res = node.getName();
        List<String> argNames = node.getArgNames();
        if (argNames.size() > 0){
            res += "("+String.join(", ", argNames)+")";
        }
        return new Result(res);
    }

    @Override
    public Result accept(FunctionCallNode node, Variables variables, Functions functions) {
        String res = node.getName() + "(";
        List<Node> arguments = node.getArguments();
        for (Node arg : arguments){
            res += arg.accept(this, variables, functions).getStr() + ", ";
        }
        res = res.substring(0, res.length()-2) + ")";
        return new Result(res);
    }

    @Override
    public Result accept(NumberNode node, Variables variables, Functions functions) {
        double value = node.getValue();
        if (value == (int)value){
            return new Result(String.valueOf((int)value));
        }
        return new Result(String.valueOf(value));
    }

    @Override
    public Result accept(VariableNode node, Variables variables, Functions functions) {
        String name = node.getName();
        return new Result(name);
    }

}

