package com.calc.command;

import com.calc.Context;
import com.calc.intrinsicFunction.IntrinsicFunction;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.List;

public class DerivationNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        Node l = node.getL();
        Node lDeriv = l.accept(this, context).getExpression();
        Node r = node.getR();
        Node rDeriv = r.accept(this, context).getExpression();
        Type operator = node.getOperator();
        switch (operator) {
            case ADD:
                return new Result(new BinaryOperatorNode(Type.ADD, lDeriv, rDeriv));
            case SUB:
                return new Result(new BinaryOperatorNode(Type.SUB, lDeriv, rDeriv));
            case MUL:
                Node firstTermMul = new BinaryOperatorNode(Type.MUL, lDeriv, r);
                Node secondTermMul = new BinaryOperatorNode(Type.MUL, l, rDeriv);
                return new Result(new BinaryOperatorNode(Type.ADD, firstTermMul, secondTermMul));
            case DIV:
                Node firstTermDiv = new BinaryOperatorNode(Type.MUL, lDeriv, r);
                Node secondTermDiv = new BinaryOperatorNode(Type.MUL, l, rDeriv);
                Node denominator = new BinaryOperatorNode(Type.POWER, r, new NumberNode(2));
                Node numerator = new BinaryOperatorNode(Type.SUB, firstTermDiv, secondTermDiv);
                return new Result(new BinaryOperatorNode(Type.DIV, numerator, denominator));
            case POWER:
                Node newPower = new BinaryOperatorNode(Type.SUB, r, new NumberNode(1));
                Node multiplier1 = new BinaryOperatorNode(Type.MUL, lDeriv, r);
                Node multiplier2 = new BinaryOperatorNode(Type.POWER, l, newPower);
                return new Result(new BinaryOperatorNode(Type.MUL, multiplier1, multiplier2));
            default:
                throw new InvalidOperationException(node, operator);
        }
    }

    @Override
    public Result accept(UnaryOperatorNode node, Context context) {
        Node operand = node.getOperand();
        Type operator = node.getOperator();
        switch (operator) {
            case SUB:
                Node expression = operand.accept(this, context).getExpression();
                return new Result(new UnaryOperatorNode(Type.SUB, expression));
            case DERIV:
                return new Result(node);
            default:
                throw new InvalidOperationException(node, operator);
        }
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        throw new InvalidOperationException(node, Type.ASS);
    }

    @Override
    public Result accept(FunctionCallNode node, Context context) {
        List<Node> arguments = node.getArguments();
        Node arg0Get = arguments.get(0);
        Result evalArg0 = arg0Get.accept(new EvalNodeVisitor(), context);
        Node arg0 = evalArg0.getExpression();
        Node argDeriv = arg0.accept(this, context).getExpression();
        Node derivFuncNode = new FunctionDerivationNode(node, 1);
        Node resultNode = new BinaryOperatorNode(Type.MUL, argDeriv, derivFuncNode);
        return resultNode.accept(new EvalNodeVisitor(), context);
    }

    @Override
    public Result accept(NumberNode node, Context context) {
        return new Result(0);
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        return new Result(1);
    }

    @Override
    public Result accept(FunctionDerivationNode node, Context context) {
        FunctionCallNode functionNode = node.getFunction();
        int derivationDegree = node.getDerivationDegree();
        List<Node> arguments = functionNode.getArguments();
        Node arg0 = arguments.get(0).accept(new EvalNodeVisitor(), context).getExpression();
        Node argDeriv = arg0.accept(this, context).getExpression();
        Node derivFuncNode = new FunctionDerivationNode(functionNode, derivationDegree+1);
        Node resultNode = new BinaryOperatorNode(Type.MUL, argDeriv, derivFuncNode);
        return resultNode.accept(new EvalNodeVisitor(), context);
    }

    @Override
    public Result accept(IntrinsicFunctionNode node, Context context) {
        IntrinsicFunction function = node.getFunction();
        return function.getDerivative();
    }
}
