package com.calc;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    private final Map<String, Float> values = new HashMap<>();

    public Float getValue(String name){
        return values.get(name);
    }

    public void createVariable(String name, float val){
        values.put(name, val);
    }
}
