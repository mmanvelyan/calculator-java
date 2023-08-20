package com.calc.command;

import com.calc.Function;
import com.calc.Functions;
import com.calc.Variables;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.ArrayList;
import java.util.List;

public class EvalNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Variables variables, Functions functions) {
        Node l = node.getL();
        Result left = l.accept(this, variables, functions);
        Node r = node.getR();
        Result right = r.accept(this, variables, functions);
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
    public Result accept(DefineNode node, Variables variables, Functions functions) {
        List<String> argNames = node.getArgNames();
        String name = node.getName();
        Node expression = node.getExpression();
        if (argNames.size() == 0){
            Result result = expression.accept(this, variables, functions);
            variables.createVariable(name, result.getVal());
            return result;
        } else {
            Function newFun = new Function(argNames, expression);
            functions.createFunction(name, newFun);
            return new Result(expression);
        }
    }

    private List<Node> evalArguments(FunctionCallNode node, Variables variables, Functions functions) {
        List<Node> arguments = new ArrayList<>();
        for (Node arg : node.getArguments()) {
            Result result = arg.accept(this, variables, functions);
            arguments.add(result.getExpression());
        }
        return arguments;
    }

    private Result commonFunctions(FunctionCallNode node, Variables variables, Functions functions){
        List<Node> arguments = evalArguments(node, variables, functions);
        String name = node.getName();
        if (arguments.size() == 1){
            Node arg = arguments.get(0);
            double value = arg.accept(this, variables, functions).getVal();
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
            double xValue = argX.accept(this, variables, functions).getVal();
            double yValue = argY.accept(this, variables, functions).getVal();
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
    public Result accept(FunctionCallNode node, Variables variables, Functions functions) {
        List<Node> arguments = evalArguments(node, variables, functions);
        String name = node.getName();
        Function function = functions.getFunction(name);
        if (function == null){
            return commonFunctions(node, variables, functions);
        }
        List<String> functionArgs = function.getArgs();
        Variables functionVariables = new Variables();
        if (arguments.size() != functionArgs.size()){
            return new Result(new FunctionCallNode(arguments, name, node.getPos()));
        }
        for (int i = 0; i < arguments.size(); i++){
            Node arg = arguments.get(i);
            Result argEval = arg.accept(this, variables, functions);
            String argName = functionArgs.get(i);
            if (argEval.getType() == ResultType.EXP){
                functionVariables.createVariable(argName, argEval.getExpression());
            } else {
                functionVariables.createVariable(argName, argEval.getVal());
            }
        }
        Result expandedResult = function.getExpression().accept(this, functionVariables, functions);
        if (expandedResult.getType() == ResultType.EXP){
            Node expandedExpression = expandedResult.getExpression();
            expandedResult = expandedExpression.accept(this, variables, functions);
        }
        return expandedResult;
    }

    @Override
    public Result accept(NumberNode node, Variables variables, Functions functions) {
        return new Result(node.getValue());
    }

    @Override
    public Result accept(VariableNode node, Variables variables, Functions functions) {
        String name = node.getName();
        Node variableValue = variables.getValue(name);
        if (variableValue == null) {
            return new Result(node);
        }
        try {
            return variableValue.accept(new EvalStrictNodeVisitor(), new Variables(), new Functions());
        } catch (Exception e) {
            return new Result(variableValue);
        }
    }
}
