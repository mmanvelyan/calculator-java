package com.calc;

import com.calc.commands.EvalNodeVisitor;
import com.calc.commands.FunctionCycleException;
import com.calc.commands.UnexpectedFunctionException;

import java.util.HashMap;
import java.util.List;
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
        List<String> args = function.getArgs();
        Variables functionVariables = new Variables();
        for (String n : args){
            functionVariables.createVariable(n, 0);
        }
        Map<String, Function> newFunctions = new HashMap<>(functions);
        newFunctions.remove(name);
        try {
            function.getExpression().accept(new EvalNodeVisitor(), functionVariables, new Functions(newFunctions));
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
