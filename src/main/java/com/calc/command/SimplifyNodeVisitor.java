package com.calc.command;

import com.calc.Context;
import com.calc.lexer.Type;
import com.calc.node.*;

public class SimplifyNodeVisitor implements NodeVisitor {
    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        Node left = node.getL();
        Node right = node.getR();
        Type operator = node.getOperator();
        Result leftRes = left.accept(this, context);
        Result rightRes = right.accept(this, context);
        switch (operator){
            case ADD:
                if (leftRes.getType() == ResultType.VAL && leftRes.getVal() == 0){
                    return rightRes;
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 0){
                    return leftRes;
                }
                break;
            case SUB:
                if (leftRes.getType() == ResultType.VAL && leftRes.getVal() == 0){
                    Node newNode = new UnaryOperatorNode(operator, rightRes.getExpression());
                    return newNode.accept(this, context);
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 0){
                    return leftRes;
                }
                break;
            case MUL:
                if (leftRes.getType() == ResultType.VAL && leftRes.getVal() == 0){
                    return new Result(0);
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 0){
                    return new Result(0);
                }
                if (leftRes.getType() == ResultType.VAL && leftRes.getVal() == 1){
                    return rightRes;
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 1){
                    return leftRes;
                }
                if (leftRes.getType() == ResultType.VAL && leftRes.getVal() == -1){
                    Node newNode = new UnaryOperatorNode(operator, rightRes.getExpression());
                    return newNode.accept(this, context);
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == -1){
                    Node newNode = new UnaryOperatorNode(operator, leftRes.getExpression());
                    return newNode.accept(this, context);
                }
                break;
            case DIV:
                if (leftRes.getType() == ResultType.VAL && leftRes.getVal() == 0){
                    return new Result(0);
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 0){
                    throw new ArithmeticException("/ by 0");
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 1){
                    return leftRes;
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == -1){
                    Node newNode = new UnaryOperatorNode(operator, leftRes.getExpression());
                    return newNode.accept(this, context);
                }
                break;
            case POWER:
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 0){
                    return new Result(1);
                }
                if (rightRes.getType() == ResultType.VAL && rightRes.getVal() == 1){
                    return leftRes;
                }
                break;
        }
        Node newNode = new BinaryOperatorNode(operator, leftRes.getExpression(), rightRes.getExpression());
        return newNode.accept(new EvalNodeVisitor(), context);
    }

    @Override
    public Result accept(UnaryOperatorNode node, Context context) {
        return new Result(node);
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        return new Result(node);
    }

    @Override
    public Result accept(FunctionCallNode node, Context context) {
        return new Result(node);
    }

    @Override
    public Result accept(NumberNode node, Context context) {
        double value = node.getValue();
        return new Result(value+0.0);
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        return new Result(node);
    }

    @Override
    public Result accept(FunctionDerivationNode node, Context context) {
        return new Result(node);
    }
}
