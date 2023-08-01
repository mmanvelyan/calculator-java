package com.calc;

public class Output {

    public static void printPositionException(PositionException e){
        int pos = e.getPos();
        for (int i = 0; i < pos; i++) {
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println(e.getMessage());
    }

    public static void printException(Exception e){
        System.out.println(e.getMessage());
    }

    public static void printResult(Result er){
        if (er.getType() == ResultType.VAL) {
            System.out.println(er.getVal());
        } else {
            System.out.println(er.getStr());
        }
    }
}

