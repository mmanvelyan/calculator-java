package com.calc;

import com.calc.node.Node;

public class Context {
    private final Variables variables;
    private final Functions functions;

    public Context(Variables variables, Functions functions) {
        this.variables = variables;
        this.functions = functions;
    }

    public Context() {
        variables = new Variables();
        functions = new Functions();
    }

    public Function getFunction(String name){
        return functions.getFunction(name);
    }

    public void createFunction(String name, Function function){
        functions.createFunction(name, function);
    }

    public Node getValue(String name){
        return variables.getValue(name);
    }

    public void createVariable(String name, double val){
        variables.createVariable(name, val);
    }

    public void createVariable(String name, Node val){
        variables.createVariable(name, val);
    }

    public Variables getVariables() {
        return variables;
    }

    public Functions getFunctions() {
        return functions;
    }
}
