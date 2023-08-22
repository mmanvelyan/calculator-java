package com.calc.command;

import com.calc.Context;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

public class PrintNodeVisitor implements NodeVisitor {

    private final Map<Character, Integer> priorities = new HashMap<>();

    public PrintNodeVisitor(){
        priorities.put('-', 0);
        priorities.put('+', 0);
        priorities.put('*', 1);
        priorities.put('/', 1);
        priorities.put('^', 2);
    }

    private int getMinPriority(String s){
        int minPriority = Integer.MAX_VALUE;
        int br = 0;
        for (int i = 0; i < s.length(); i++){
            char curChar = s.charAt(i);
            if (br == 0){
                Integer curPriority = priorities.get(curChar);
                if (curPriority != null){
                    minPriority = min(curPriority, minPriority);
                } else if (curChar == '('){
                    br++;
                }
            } else if (curChar == '('){
                br++;
            } else if (curChar == ')'){
                br--;
            }
        }
        return minPriority;
    }

    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        Node l = node.getL();
        String leftNode = l.accept(this, context).getStr();
        Node r = node.getR();
        String rightNode = r.accept(this, context).getStr();
        Type operator = node.getOperator();
        char operatorChar = operator.toString().charAt(0);
        Integer operatorPriority = priorities.get(operatorChar);
        if (operatorPriority > getMinPriority(leftNode)){
            leftNode = '(' + leftNode + ')';
        }
        if (operatorPriority >= getMinPriority(rightNode)){
            rightNode = '(' + rightNode + ')';
        }
        return new Result(leftNode+operatorChar+rightNode);
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        String res = node.getName();
        List<String> argNames = node.getArgNames();
        if (argNames.size() > 0){
            res += "("+String.join(", ", argNames)+")";
        }
        return new Result(res);
    }

    @Override
    public Result accept(FunctionCallNode node, Context context) {
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
        double value = node.getValue();
        if (value == (int)value){
            return new Result(String.valueOf((int)value));
        }
        return new Result(String.valueOf(value));
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        String name = node.getName();
        return new Result(name);
    }

}

