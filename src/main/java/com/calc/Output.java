package com.calc;

import javafx.geometry.Pos;

public class Output {
    public static void printToken(Token token){
        if (token.getType() == Type.NUM){
            System.out.print(token.getVal());
        } else if (token.getType() == Type.VAR){
            System.out.print(token.getName());
        } else {
            System.out.print(token.getType().toString());
        }
        System.out.print(" ");
    }

    public static void printPositionException(PosisionException e){
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

    public static void printTreePostfix(Node n){
        Node l = n.getL(), r = n.getR();
        if (l != null) {
            printTreePostfix(l);
        }
        if (r != null){
            printTreePostfix(r);
        }
        printToken(n.getToken());
    }
    public static void printResult(EvalResult er){
        System.out.println(er.getRes());
    }
}

