package com.calc;

public class EvalResult {
    private final EvalResultType type;
    private String msg;
    private float res;
    EvalResult(String s){
        type = EvalResultType.MSG;
        msg = s;
    }
    EvalResult(float f){
        type = EvalResultType.RES;
        res = f;
    }
    EvalResultType getType(){
        return type;
    }
    String getMessage(){
        return msg;
    }
    float getRes(){
        return res;
    }
}
