package com.calc;
import java.util.Scanner;

public class Main {
    static Node vyrazhenie(String s){
        //System.out.println("vyrazhenie"+s);
        if (s.length() == 0){
            return new Node(nType.num, null, null, 0);
        }
        if (s.charAt(0) == '('){
            if (s.charAt(s.length()-1) == ')'){
                return slag(s.substring(1, s.length()-1));
            } else {
                throw new BracketsException("ERROR: missing ')'");
            }
        } else if (s.charAt(s.length()-1) == ')'){
            throw new BracketsException("ERROR: missing '('");
        }
        return new Node(nType.num, null, null, Float.parseFloat(s));
    }
    static Node mnozh(String s){
        //System.out.println("mnozh"+s);
        int i = s.length()-1, br = 0;
        while (i >= 0){
            if (s.charAt(i) == '(') {
                br--;
                if (br < 0){
                    throw new BracketsException("ERROR: missing ')'");
                }
            }
            if (s.charAt(i) == ')'){
                br++;

            }
            if (br == 0){
                if (s.charAt(i) == '*'){
                    return new Node(nType.mul, mnozh(s.substring(0, i)), vyrazhenie(s.substring(i+1)));
                }
                if (s.charAt(i) == '/'){
                    return new Node(nType.div, mnozh(s.substring(0, i)), vyrazhenie(s.substring(i+1)));
                }
            }
            i--;
        }
        return vyrazhenie(s);
    }
    static Node slag(String s){
        //System.out.println("slag"+s);
        int i = s.length()-1, br = 0;
        while (i >= 0){
            if (s.charAt(i) == '(') {
                br--;
                if (br < 0){
                    throw new BracketsException("ERROR: missing ')'");
                }
            }
            if (s.charAt(i) == ')'){
                br++;

            }
            if (br == 0){
                if (s.charAt(i) == '+'){
                    return new Node(nType.add, slag(s.substring(0, i)), mnozh(s.substring(i+1)));
                }
                if (s.charAt(i) == '-'){
                    return new Node(nType.sub, slag(s.substring(0, i)), mnozh(s.substring(i+1)));
                }
            }
            i--;
        }
        return mnozh(s);
    }

    static float eval(Node tr){
        if (tr.tp == nType.num){
            return tr.val;
        }
        if (tr.tp == nType.add){
            return eval(tr.l)+eval(tr.r);
        }
        if (tr.tp == nType.sub){
            return eval(tr.l)-eval(tr.r);
        }
        if (tr.tp == nType.mul){
            return eval(tr.l)*eval(tr.r);
        }
        if (tr.tp == nType.div){
            if (eval(tr.r) == 0){
                throw new ArithmeticException("/ by 0");
            }
            return eval(tr.l)/eval(tr.r);
        }
        return 0;
    }

    public static float calculate(String s){
        s = s.replace(" ", "");
        Node tree = slag(s);
        float res = 0;
        return eval(tree);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String s = in.nextLine();
            System.out.println(calculate(s));
        }
    }
}