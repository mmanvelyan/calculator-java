package com.calc;

import com.calc.command.*;

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
        if (er.getType() == ResultType.STR){
            System.out.println(er.getStr());
        } else {
            NodeVisitor print = new PrintNodeVisitor();
            Result newRes = er.getExpression().accept(new SimplifyNodeVisitor(), new Context());
            System.out.println(newRes.getExpression().accept(print, new Context()).getStr());
        }
    }
}

