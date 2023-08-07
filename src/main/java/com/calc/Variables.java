package com.calc;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private final Map<String, Double> values = new HashMap<>();

    public Variables(){
        addCommonVariables();
    }

    public Double getValue(String name){
        return values.get(name);
    }

    public void createVariable(String name, double val){
        values.put(name, val);
    }

    private void addCommonVariables(){
        values.put("pi", Math.PI);
        values.put("e", Math.E);
        values.put("degree", Math.PI/180);
    }
}
