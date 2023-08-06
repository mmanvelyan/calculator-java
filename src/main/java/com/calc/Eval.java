package com.calc;

import java.util.ArrayList;

public class Eval extends Command {

    public Result execute(Node expression, Variables variables, Functions functions){
        return eval(expression, variables, functions);
    }

    private Result evalFunction(Token token, Variables variables, Functions functions){
        Function function = functions.getFunction(token.getName());
        ArrayList<Node> args = token.getArgs();
        ArrayList<Node> functionArgs = function.getArgs();
        Variables functionVariables = new Variables();
        if (args.size() != functionArgs.size()){
            throw new UnexpectedFunctionException(token);
        }
        for (int i = 0; i < args.size(); i++){
            functionVariables.createVariable(functionArgs.get(i).getToken().getName(), eval(args.get(i), variables, functions).getVal());
        }
        return eval(function.getExpression(), functionVariables, functions);
    }

    private Result eval(Node n, Variables variables, Functions functions) {
        Token token = n.getToken();
        Node l = n.getL();
        Node r = n.getR();
        switch (token.getType()) {
            case FUN:
                if (functions.getFunction(token.getName()) == null){
                    throw new UnexpectedFunctionException(token);
                }
                return evalFunction(token, variables, functions);
            case ASS:
                if (l.getToken().getType() == Type.VAR){
                    float value = eval(r, variables, functions).getVal();
                    variables.createVariable(l.getToken().getName(), value);
                    return new Result(value);
                } else if (l.getToken().getType() == Type.FUN){
                    Node expression = r;
                    functions.createFunction(l.getToken().getName(), new Function(l.getToken().getArgs(), r));
                    return new Result(0);
                }
            case VAR:
                if (variables.getValue(token.getName()) == null) {
                    throw new UnexpectedVariableException(token);
                }
                return new Result(variables.getValue(token.getName()));
            case NUM:
                return new Result(token.getVal());
            case ADD:
                return new Result(eval(l, variables, functions).getVal() + eval(r, variables, functions).getVal());
            case SUB:
                return new Result(eval(l, variables, functions).getVal() - eval(r, variables, functions).getVal());
            case MUL:
                return new Result(eval(l, variables, functions).getVal() * eval(r, variables, functions).getVal());
            case DIV:
                float rEval = eval(r, variables, functions).getVal();
                if (rEval == 0) {
                    throw new ArithmeticException("/ by 0");
                }
                return new Result(eval(l, variables, functions).getVal() / rEval);
            default:
                return new Result(0);
        }
    }
}

