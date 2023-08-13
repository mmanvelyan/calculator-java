package com.calc;

import java.util.ArrayList;

public class FunctionCallNode implements Node {
    private final ArrayList<Node> arguments;
    private final String name;
    private final int pos;

    public FunctionCallNode(ArrayList<Node> arguments, String name, int pos) {
        this.arguments = arguments;
        this.name = name;
        this.pos = pos;
    }

    private Result commonFunctions(Variables variables, Functions functions){
        if (arguments.size() == 1){
            Node arg = arguments.get(0);
            switch (name) {
                case "sqrt":
                    return new Result(Math.sqrt(arg.eval(variables, functions).getVal()));
                case "exp":
                    return new Result(Math.exp(arg.eval(variables, functions).getVal()));
                case "ln":
                    return new Result(Math.log(arg.eval(variables, functions).getVal()));
                case "lg":
                    return new Result(Math.log10(arg.eval(variables, functions).getVal()));
                case "sin":
                    return new Result(Math.sin(arg.eval(variables, functions).getVal()));
                case "cos":
                    return new Result(Math.cos(arg.eval(variables, functions).getVal()));
                case "tan":
                    return new Result(Math.tan(arg.eval(variables, functions).getVal()));
                case "arcsin":
                    return new Result(Math.asin(arg.eval(variables, functions).getVal()));
                case "arccos":
                    return new Result(Math.acos(arg.eval(variables, functions).getVal()));
                case "arctan":
                    return new Result(Math.atan(arg.eval(variables, functions).getVal()));
                case "abs":
                    return new Result(Math.abs(arg.eval(variables, functions).getVal()));
                default:
                    throw new UnexpectedFunctionException(name, pos);
            }
        } else if (arguments.size() == 2){
            Node argX = arguments.get(0);
            Node argY = arguments.get(1);
            switch (name) {
                case "log":
                    return new Result((Math.log(argY.eval(variables, functions).getVal()) / Math.log(argX.eval(variables, functions).getVal())));
                case "mod":
                    return new Result(((argX.eval(variables, functions).getVal() % argY.eval(variables, functions).getVal())));
                default:
                    throw new UnexpectedFunctionException(name, pos);
            }
        } else {
            throw new UnexpectedFunctionException(name, pos);
        }
    }

    public Result eval(Variables variables, Functions functions) {
        if (functions.getFunction(name) == null){
            return commonFunctions(variables, functions);
        }
        Function function = functions.getFunction(name);
        ArrayList<String> functionArgs = function.getArgs();
        Variables functionVariables = new Variables();
        if (arguments.size() != functionArgs.size()){
            throw new UnexpectedFunctionException(name, pos);
        }
        for (int i = 0; i < arguments.size(); i++){
            functionVariables.createVariable(functionArgs.get(i), arguments.get(i).eval(variables, functions).getVal());
        }
        return function.getExpression().eval(functionVariables, functions);
    }

    public String rpn(Variables variables, Functions functions) {
        String res = name + "(";
        for (Node arg : arguments){
            res += arg.rpn(variables, functions) + ", ";
        }
        res = res.substring(0, res.length()-3) + ") ";
        return res;
    }

}
