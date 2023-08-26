package com.calc.command;

import com.calc.Context;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PrintNodeVisitor implements NodeVisitor {

    private final Map<Character, Integer> priorities = new HashMap<>();
    private int minPriority;

    public PrintNodeVisitor(){
        priorities.put('-', 0);
        priorities.put('+', 0);
        priorities.put('*', 1);
        priorities.put('/', 1);
        priorities.put('^', 2);
    }

    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        minPriority = Integer.MAX_VALUE;
        Node l = node.getL();
        String leftNode = l.accept(this, context).getStr();
        int leftPriority = minPriority;
        minPriority = Integer.MAX_VALUE;
        Node r = node.getR();
        String rightNode = r.accept(this, context).getStr();
        int rightPriority = minPriority;
        Type operator = node.getOperator();
        char operatorChar = operator.toString().charAt(0);
        Integer operatorPriority = priorities.get(operatorChar);
        minPriority = operatorPriority;
        if (operatorPriority > leftPriority){
            leftNode = '(' + leftNode + ')';
        }
        if (operatorPriority >= rightPriority){
            rightNode = '(' + rightNode + ')';
        }
        return new Result(leftNode+operatorChar+rightNode);
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        minPriority = Integer.MAX_VALUE;
        String res = node.getName();
        List<String> argNames = node.getArgNames();
        if (argNames.size() > 0){
            res += "("+String.join(", ", argNames)+")";
        }
        return new Result(res);
    }

    @Override
    public Result accept(FunctionCallNode node, Context context) {
        minPriority = Integer.MAX_VALUE;
        String res = node.getName() + "(";
        List<Node> arguments = node.getArguments();
        for (Node arg : arguments){
            res += arg.accept(this, context).getStr() + ", ";
        }
        res = res.substring(0, res.length()-2) + ")";
        return new Result(res);
    }

    @Override
    public Result accept(NumberNode node, Context context) {
        minPriority = Integer.MAX_VALUE;
        double value = node.getValue();
        if (value == (int)value){
            return new Result(String.valueOf((int)value));
        }
        return new Result(String.valueOf(value));
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        minPriority = Integer.MAX_VALUE;
        String name = node.getName();
        return new Result(name);
    }

}

