package com.calc.commands;

import com.calc.*;
import com.calc.lexer.Type;
import com.calc.nodes.*;

import java.util.List;

public class EvalNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Variables variables, Functions functions) {
        double leftValue = node.getL().accept(this, variables, functions).getVal();
        double rightValue = node.getR().accept(this, variables, functions).getVal();
        Type operator = node.getOperator();
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
            return new Result(0);
        }
    }

    private Result commonFunctions(List<Node> arguments, String name, int pos, Variables variables, Functions functions){
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
                    throw new UnexpectedFunctionException(name, pos);
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
                    throw new UnexpectedFunctionException(name, pos);
            }
        } else {
            throw new UnexpectedFunctionException(name, pos);
        }
    }

    @Override
    public Result accept(FunctionCallNode node, Variables variables, Functions functions) {
        List<Node> arguments = node.getArguments();
        String name = node.getName();
        int pos = node.getPos();
        if (functions.getFunction(name) == null){
            return commonFunctions(arguments, name, pos, variables, functions);
        }
        Function function = functions.getFunction(name);
        List<String> functionArgs = function.getArgs();
        Variables functionVariables = new Variables();
        if (arguments.size() != functionArgs.size()){
            throw new UnexpectedFunctionException(name, pos);
        }
        for (int i = 0; i < arguments.size(); i++){
            Node arg = arguments.get(i);
            functionVariables.createVariable(functionArgs.get(i), arg.accept(this, variables, functions).getVal());
        }
        return function.getExpression().accept(this, functionVariables, functions);
    }

    @Override
    public Result accept(NumberNode node, Variables variables, Functions functions) {
        return new Result(node.getValue());
    }

    @Override
    public Result accept(VariableNode node, Variables variables, Functions functions) {
        String name = node.getName();
        int pos = node.getPos();
        if (variables.getValue(name) == null) {
            throw new UnexpectedVariableException(name, pos);
        }
        return new Result(variables.getValue(name));
    }
}
