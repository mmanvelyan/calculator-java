package com.calc;

public class Result {
    private final double val;
    private final String str;
    private final ResultType type;

    public Result(double f){
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

    public double getVal(){
        return val;
    }

    public String getStr(){
        return str;
    }
}
