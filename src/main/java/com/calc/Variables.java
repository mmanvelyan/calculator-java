package com.calc;

import java.util.HashMap;
import java.util.Map;

public class Variables {
    final private Map<String, Float> values;
    public Variables () {
        values = new HashMap<>();
    }

    public float getValue(String name){
        if (values.get(name) == null){
            throw new UnexpectedTokenException(0, name, "variable name");
        }
        return values.get(name);
    }

    private boolean checkVariableString(String s){
        if (s.lastIndexOf('=') != s.indexOf('=')){
            throw new UnexpectedTokenException(s.lastIndexOf('='), "=", "expression");
        } else {
            return s.indexOf('=') != -1;
        }
    }

    private boolean checkName(String s){
        if (s.length() == 0){
            throw new UnexpectedTokenException(0, "=", "variable name");
        }
        if (!Character.isLetter(s.charAt(0))){
            throw new UnexpectedTokenException(0, s.substring(0, 1), "letter");
        }
        for (int i = 1; i < s.length(); i++){
            if (!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i))){
                throw new UnexpectedTokenException(i, s.substring(i, i+1), "letter or digit");
            }
        }
        return true;
    }
    public boolean createVariable(Calculator calc, String s){
        if (!checkVariableString(s)){
            return false;
        }
        int eqIdx = s.indexOf('=');
        String varName = s.substring(0, eqIdx);
        String varValue = s.substring(eqIdx+1);
        float value = calc.calculate(varValue);
        if (checkName(varName)){
            values.put(varName, value);
        }
        return true;
    }
}
