package com.calc;

public class Result {
    private final float val;
    private final String str;
    private final ResultType type;

    public Result(float f){
        type = ResultType.VAL;
        val = f;
        str = "";
    }

    public Result(String s){
        type = ResultType.STR;
        val = 0;
        str = s;
    }

    public ResultType getType(){
        return type;
    }

    public float getVal(){
        return val;
    }

    public String getStr(){
        return str;
    }
}
