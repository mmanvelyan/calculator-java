package com.calc.command;

import com.calc.Context;
import com.calc.Function;
import com.calc.Variables;
import com.calc.lexer.Type;
import com.calc.node.*;

import java.util.List;

public class EvalStrictNodeVisitor implements NodeVisitor {

    @Override
    public Result accept(BinaryOperatorNode node, Context context) {
        double leftValue = node.getL().accept(this, context).getVal();
        double rightValue = node.getR().accept(this, context).getVal();
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
    public Result accept(UnaryOperatorNode node, Context context) {
        Node operand = node.getOperand();
        double value = operand.accept(this, context).getVal();
        Type operator = node.getOperator();
        switch (operator) {
            case SUB:
                return new Result(-value);
            default:
                throw new InvalidOperationException(node, operator);
        }
    }

    @Override
    public Result accept(DefineNode node, Context context) {
        List<String> argNames = node.getArgNames();
        String name = node.getName();
        Node expression = node.getExpression();
        if (argNames.size() == 0){
            Result result = expression.accept(this, context);
            context.createVariable(name, result.getVal());
            return result;
        } else {
            Function newFun = new Function(argNames, expression);
            Result result = expression.accept(this, context);
            context.createFunction(name, newFun);
            return result;
        }
    }

    private Result commonFunctions(List<Node> arguments, String name, int pos, Context context){
        if (arguments.size() == 1){
            Node arg = arguments.get(0);
            double value = arg.accept(this, context).getVal();
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
            double xValue = argX.accept(this, context).getVal();
            double yValue = argY.accept(this, context).getVal();
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
    public Result accept(FunctionCallNode node, Context context) {
        List<Node> arguments = node.getArguments();
        String name = node.getName();
        int pos = node.getPos();
        if (context.getFunction(name) == null){
            return commonFunctions(arguments, name, pos, context);
        }
        Function function = context.getFunction(name);
        List<String> functionArgs = function.getArgs();
        Context functionContext = new Context(new Variables(), context.getFunctions());
        if (arguments.size() != functionArgs.size()){
            throw new UnexpectedFunctionException(name, pos);
        }
        for (int i = 0; i < arguments.size(); i++){
            Node arg = arguments.get(i);
            Result argEval = arg.accept(new EvalNodeVisitor(), context);
            String argName = functionArgs.get(i);
            if (argEval.getType() == ResultType.VAL){
                functionContext.createVariable(argName, argEval.getVal());
            } else {
                functionContext.createVariable(argName, argEval.getExpression());
            }
        }
        Node functionExpression = function.getExpression();
        return functionExpression.accept(this, functionContext);
    }

    @Override
    public Result accept(NumberNode node, Context context) {
        return new Result(node.getValue());
    }

    @Override
    public Result accept(VariableNode node, Context context) {
        String name = node.getName();
        int pos = node.getPos();
        Node value = context.getValue(name);
        if (value == null) {
            throw new UnexpectedVariableException(name, pos);
        }
        return value.accept(this, context);
    }
}
