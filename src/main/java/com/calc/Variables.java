package com.calc;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private final Map<String, Float> values = new HashMap<>();

    public float getValue(String name){
        if (values.get(name) == null){
            throw new UnexpectedTokenException(0, name, "variable name");
        }
        return values.get(name);
    }

    public void createVariable(String name, float val){
        values.put(name, val);
    }
}
