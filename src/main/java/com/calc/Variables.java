package com.calc;

import com.calc.node.Node;
import com.calc.node.NumberNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variables {
    private final Map<String, Node> values = new HashMap<>();

    public Variables(){
        addCommonVariables();
    }

    public Node getValue(String name){
        Node valueNode = values.get(name);
        return valueNode;
    }

    public void createVariable(String name, double val){
        Node valueNode = new NumberNode(val);
        values.put(name, valueNode);
    }

    public void createVariable(String name, Node val){
        values.put(name, val);
    }

    private void addCommonVariables(){
        createVariable("pi", Math.PI);
        createVariable("e", Math.E);
        createVariable("degree", Math.PI/180);
    }

    public String toString(){
        String res = "variables {";
        List<String> variablesStrings = new ArrayList<>();
        for ( Map.Entry<String, Node> f : values.entrySet()){
            variablesStrings.add(f.getKey() + " : " + f.getValue().toString());
        }
        return res + String.join(", ", variablesStrings) + "}";
    }

}
