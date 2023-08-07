package com.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Functions {

    private final Map<String, Function> functions;


    public Functions() {
        functions = new HashMap<>();
        addCommonFunctions();
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

    private void addCommonFunctions(){
        ArrayList<Node> argsX = new ArrayList<>();
        ArrayList<Node> argsXY = new ArrayList<>();
        Token x = new Token("x", -1);
        Token y = new Token("y", -1);
        argsXY.add(new Node(x, null, null));
        argsXY.add(new Node(y, null, null));
        argsX.add(new Node(x, null, null));
        functions.put("sqrt", new Function(argsX, new Node(new Token(Type.SQRT), new Node(x, null, null), null)));
        functions.put("exp", new Function(argsX, new Node(new Token(Type.EXP), new Node(x, null, null), null)));
        functions.put("ln", new Function(argsX, new Node(new Token(Type.LN), new Node(x, null, null), null)));
        functions.put("lg", new Function(argsX, new Node(new Token(Type.LG), new Node(x, null, null), null)));
        functions.put("log", new Function(argsXY, new Node(new Token(Type.LOG), new Node(x, null, null), new Node(y, null, null))));
        functions.put("sin", new Function(argsX, new Node(new Token(Type.SIN), new Node(x, null, null), null)));
        functions.put("cos", new Function(argsX, new Node(new Token(Type.COS), new Node(x, null, null), null)));
        functions.put("tan", new Function(argsX, new Node(new Token(Type.TAN), new Node(x, null, null), null)));
        functions.put("arcsin", new Function(argsX, new Node(new Token(Type.ARCSIN), new Node(x, null, null), null)));
        functions.put("arccos", new Function(argsX, new Node(new Token(Type.ARCCOS), new Node(x, null, null), null)));
        functions.put("arctan", new Function(argsX, new Node(new Token(Type.ARCTAN), new Node(x, null, null), null)));
        functions.put("abs", new Function(argsX, new Node(new Token(Type.ABS), new Node(x, null, null), null)));
        functions.put("mod", new Function(argsXY, new Node(new Token(Type.MOD), new Node(x, null, null), new Node(y, null, null))));
    }

}
