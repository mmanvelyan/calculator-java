package com.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Functions {

    private final Map<String, Function> functions;


    public Functions() {
        functions = new HashMap<>();
    }

    public Functions(Map<String, Function> functions) {
        this.functions = functions;
    }

    public Function getFunction(String name){
        return functions.get(name);
    }

    public void createFunction(String name, Function function){
        ArrayList<Node> args = function.getArgs();
        Variables functionVariables = new Variables();
        for (Node n : args){
            if (n.getToken().getType() != Type.VAR){
                throw new UnexpectedArgumentException(n.getToken());
            }
            functionVariables.createVariable(n.getToken().getName(), 0);
        }
        Eval e = new Eval();
        Map<String, Function> newFunctions = new HashMap<>(functions);
        newFunctions.remove(name);
        try {
            e.execute(function.getExpression(), functionVariables, new Functions(newFunctions));
        } catch (UnexpectedFunctionException ex){
            if (functions.get(ex.getName()) == null){
                throw ex;
            } else {
                throw new FunctionCycleException(ex);
            }
        }
        functions.put(name, function);
    }
}
