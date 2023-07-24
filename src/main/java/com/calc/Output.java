package com.calc;

public class Output {
    public static void printToken(Token v){
        if (v.getType() == Type.NUM){
            System.out.print(v.getVal());
        } else if (v.getType() == Type.VAR){
            System.out.print(v.getName());
        } else {
            System.out.print(v.getType().toString());
        }
        System.out.print(" ");
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
    public static void printUnexpectedToken(UnexpectedTokenException ex){
        System.out.println(ex.getS());
        int pos = ex.getPos();
        for (int i = 0; i < pos; i++) {
            System.out.print(" ");
        }
        System.out.println("^");
        System.out.println(ex.getMessage());
    }

    public static void printResult(EvalResult er){
        if (er.getType() == EvalResultType.MSG){
            System.out.println(er.getMessage());
        } else {
            System.out.println(er.getRes());
        }
    }
}
