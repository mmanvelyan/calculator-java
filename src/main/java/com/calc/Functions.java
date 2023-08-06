package com.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Functions {

    private final Map<String, Function> functions = new HashMap<>();

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
        e.execute(function.getExpression(), functionVariables, this);
        functions.put(name, function);
    }
}
