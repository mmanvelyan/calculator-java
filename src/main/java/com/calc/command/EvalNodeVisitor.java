package com.calc.command;

import com.calc.Context;
import com.calc.Function;
import com.calc.Variables;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.ArrayList;
import java.util.List;

public class EvalNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        Node l = node.getL();
        Result left = l.accept(this, context);
        Node r = node.getR();
        Result right = r.accept(this, context);
        Type operator = node.getOperator();
        if (left.getType() == ResultType.VAL && right.getType() == ResultType.VAL) {
            double leftValue = left.getVal();
            double rightValue = right.getVal();
            switch (operator) {
                case ADD:
                    return new Result(leftValue + rightValue);
                case SUB:
                    return new Result(leftValue - rightValue);
                case MUL:
                    return new Result(leftValue * rightValue);
                case DIV:
                    if (rightValue == 0) {
                        throw new ArithmeticException("/ by 0");
                    }
                    return new Result(leftValue / rightValue);
                case POWER:
                    return new Result(Math.pow(leftValue, rightValue));
                default:
                    throw new InvalidOperationException(node, operator);
            }
        } else {
            Node leftExpression = left.getExpression();
            Node rightExpression = right.getExpression();
            Result result = new Result(new BinaryOperatorNode(operator, leftExpression, rightExpression));
            return result;
        }
    }

    @Override
    public Result accept(UnaryOperatorNode node, Context context) {
        Node operand = node.getOperand();
        Result res = operand.accept(this, context);
        Type operator = node.getOperator();
        if (res.getType() == ResultType.VAL) {
            double value = res.getVal();
            switch (operator) {
                case SUB:
                    return new Result(-value);
                case DERIV:
                    return new Result(0);
                default:
                    throw new InvalidOperationException(node, operator);
            }
        } else {
            Node expression = res.getExpression();
            switch (operator) {
                case SUB:
                    return new Result(new UnaryOperatorNode(Type.SUB, expression));
                case DERIV:
                    Node derivative = expression.accept(new DerivationNodeVisitor(), context).getExpression();
                    return derivative.accept(this, context);
                default:
                    throw new InvalidOperationException(node, operator);
            }
        }
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        List<String> argNames = node.getArgNames();
        String name = node.getName();
        Node expression = node.getExpression();
        if (argNames.size() == 0){
            Result result = expression.accept(new EvalStrictNodeVisitor(), context);
            context.createVariable(name, result.getVal());
            return result;
        } else {
            Function newFun = new Function(argNames, expression);
            context.createFunction(name, newFun);
            return new Result(expression);
        }
    }

    private List<Node> evalArguments(FunctionCallNode node, Context context) {
        List<Node> arguments = new ArrayList<>();
        for (Node arg : node.getArguments()) {
            Result result = arg.accept(this, context);
            arguments.add(result.getExpression());
        }
        return arguments;
    }

    private Result commonFunctions(FunctionCallNode node, Context context){
        List<Node> arguments = evalArguments(node, context);
        String name = node.getName();
        if (arguments.size() == 1){
            Node arg = arguments.get(0);
            Result argRes = arg.accept(this, context);
            if (argRes.getType() != ResultType.VAL){
                return new Result(new FunctionCallNode(arguments, name, node.getPos()));
            }
            double value = argRes.getVal();
            switch (name) {
                case "sqrt":
                    return new Result(Math.sqrt(value));
                case "exp":
                    return new Result(Math.exp(value));
                case "ln":
                    return new Result(Math.log(value));
                case "lg":
                    return new Result(Math.log10(value));
                case "sin":
                    return new Result(Math.sin(value));
                case "cos":
                    return new Result(Math.cos(value));
                case "tan":
                    return new Result(Math.tan(value));
                case "arcsin":
                    return new Result(Math.asin(value));
                case "arccos":
                    return new Result(Math.acos(value));
                case "arctan":
                    return new Result(Math.atan(value));
                case "abs":
                    return new Result(Math.abs(value));
                default:
                    return new Result(new FunctionCallNode(arguments, name, node.getPos()));
            }
        } else if (arguments.size() == 2){
            Node argX = arguments.get(0);
            Node argY = arguments.get(1);
            Result argXRes = argX.accept(this, context);
            Result argYRes = argY.accept(this, context);
            if (argXRes.getType() != ResultType.VAL || argYRes.getType() != ResultType.VAL){
                return new Result(new FunctionCallNode(arguments, name, node.getPos()));
            }
            double xValue = argX.accept(this, context).getVal();
            double yValue = argY.accept(this, context).getVal();
            switch (name) {
                case "log":
                    return new Result(Math.log(yValue) / Math.log(xValue));
                case "mod":
                    return new Result(xValue % yValue);
                default:
                    return new Result(new FunctionCallNode(arguments, name, node.getPos()));
            }
        } else {
            return new Result(new FunctionCallNode(arguments, name, node.getPos()));
        }
    }

    @Override
    public Result accept(FunctionCallNode node, Context context) {
        List<Node> arguments = evalArguments(node, context);
        String name = node.getName();
        Function function = context.getFunction(name);
        if (function == null){
            return commonFunctions(node, context);
        }
        List<String> functionArgs = function.getArgs();
        Variables functionVariables = new Variables();
        if (arguments.size() != functionArgs.size()){
            return new Result(new FunctionCallNode(arguments, name, node.getPos()));
        }
        for (int i = 0; i < arguments.size(); i++){
            Node arg = arguments.get(i);
            Result argEval = arg.accept(this, context);
            String argName = functionArgs.get(i);
            if (argEval.getType() == ResultType.EXP){
                functionVariables.createVariable(argName, argEval.getExpression());
            } else {
                functionVariables.createVariable(argName, argEval.getVal());
            }
        }
        Context functionContext = new Context(functionVariables, context.getFunctions());
        Node functionExpression = function.getExpression();
        Result expandedResult = functionExpression.accept(this, functionContext);
        if (expandedResult.getType() == ResultType.EXP){
            Node expandedExpression = expandedResult.getExpression();
            expandedResult = expandedExpression.accept(this, context);
        }
        return expandedResult;
    }

    @Override
    public Result accept(NumberNode node, Context context) {
        return new Result(node.getValue());
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        String name = node.getName();
        Node variableValue = context.getValue(name);
        if (variableValue == null) {
            return new Result(node);
        }
        try {
            return variableValue.accept(new EvalStrictNodeVisitor(), new Context());
        } catch (Exception e) {
            return new Result(variableValue);
        }
    }

    @Override
    public Result accept(FunctionDerivationNode node, Context context) {
        FunctionCallNode functionCallNode = node.getFunction();
        int derivationDegree = node.getDerivationDegree();
        int pos = functionCallNode.getPos();
        String functionName = functionCallNode.getName();
        List<Node> arguments = evalArguments(functionCallNode, context);
        Function function = context.getFunction(functionName);
        if (function == null){
            return new Result(new FunctionDerivationNode(new FunctionCallNode(arguments, functionName, pos), derivationDegree));
        }
        List<String> funArguments = function.getArgs();
        if (funArguments.size() != arguments.size()){
            return new Result(new FunctionDerivationNode(new FunctionCallNode(arguments, functionName, pos), derivationDegree));
        }
        Node functionDefinition = function.getExpression();
        DerivationNodeVisitor derivationNodeVisitor = new DerivationNodeVisitor();
        Context emptyVariablesContext = new Context(new Variables(), context.getFunctions());
        for (int i = 0; i < derivationDegree; i++){
            Result derivationRes = functionDefinition.accept(derivationNodeVisitor, emptyVariablesContext);
            functionDefinition = derivationRes.getExpression();
        }
        Function derivFunction = new Function(funArguments, functionDefinition);
        String newName = functionName;
        for (int i = 0; i < derivationDegree; i++) {
            newName += "'";
        }
        context.createFunction(newName, derivFunction);
        Node newCall = new FunctionCallNode(arguments, newName, pos);
        return newCall.accept(this, context);
    }
}
