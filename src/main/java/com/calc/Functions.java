package com.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Functions {

    private final Map<String, Function> functions;


    public Functions() {
        functions = new HashMap<>();
        //addCommonFunctions();
    }

    public Functions(Map<String, Function> functions) {
        this.functions = functions;
    }

    public Function getFunction(String name){
        return functions.get(name);
    }

    public void createFunction(String name, Function function){
        ArrayList<String> args = function.getArgs();
        Variables functionVariables = new Variables();
        for (String n : args){
            functionVariables.createVariable(n, 0);
        }
        Map<String, Function> newFunctions = new HashMap<>(functions);
        newFunctions.remove(name);
        try {
            function.getExpression().eval(functionVariables, new Functions(newFunctions));
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
