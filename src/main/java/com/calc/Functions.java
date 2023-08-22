package com.calc;

import com.calc.command.EvalStrictNodeVisitor;
import com.calc.command.FunctionCycleException;
import com.calc.command.UnexpectedFunctionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<String> args = function.getArgs();
        //0984800699
        Map<String, Function> newFunctions = new HashMap<>(functions);
        newFunctions.remove(name);
        Context functionContext = new Context(new Variables(), new Functions(newFunctions));
        for (String n : args){
            functionContext.createVariable(n, 0);
        }
        try {
            function.getExpression().accept(new EvalStrictNodeVisitor(), functionContext);
        } catch (UnexpectedFunctionException ex){
            String functionName = ex.getName();
            if (functions.get(functionName) == null){
                throw ex;
            } else {
                throw new FunctionCycleException(ex);
            }
        }
        functions.put(name, function);
    }

    public String toString(){
        String res = "functions {";
        List<String> functionsStrings = new ArrayList<>();
        for ( Map.Entry<String, Function> f : functions.entrySet()){
            functionsStrings.add(f.getKey() + " : " + f.getValue().toString());
        }
        return res + String.join(", ", functionsStrings) + "}";
    }

}
