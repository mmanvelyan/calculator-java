package com.calc;

import com.calc.command.NodeVisitor;
import com.calc.command.PrintNodeVisitor;
import com.calc.command.Result;
import com.calc.command.ResultType;

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
        } else if (er.getType() == ResultType.STR){
            System.out.println(er.getStr());
        } else {
            NodeVisitor print = new PrintNodeVisitor();
            System.out.println(er.getExpression().accept(print, new Context()).getStr());
        }
    }
}

