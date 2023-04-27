package com.calc;
import sun.security.util.PendingException;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static com.calc.Type.ADD;
import static com.calc.Type.NUM;

public class Calculator {
    static Token nxt;
    static float eval(Node tr){
        if (tr.tp == Type.NUM){
            return tr.val;
        }
        if (tr.tp == ADD){
            return eval(tr.l)+eval(tr.r);
        }
        if (tr.tp == Type.SUB){
            return eval(tr.l)-eval(tr.r);
        }
        if (tr.tp == Type.MUL){
            return eval(tr.l)*eval(tr.r);
        }
        if (tr.tp == Type.DIV){
            if (eval(tr.r) == 0){
                throw new ArithmeticException("/ by 0");
            }
            return eval(tr.l)/eval(tr.r);
        }
        return 0;
    }

    static Token nextToken(String s, AtomicInteger pos){
        if (pos.intValue() >= s.length()){
            return new Token(Type.END);
        } else if (s.charAt(pos.intValue()) == '('){
            pos.set(pos.intValue()+1);
            return new Token(Type.OPEN_BR);
        } else if (s.charAt(pos.intValue()) == ')'){
            pos.set(pos.intValue()+1);
            return new Token(Type.CLOSING_BR);
        } else if (s.charAt(pos.intValue()) == '+'){
            pos.set(pos.intValue()+1);
            return new Token(ADD);
        } else if (s.charAt(pos.intValue()) == '-'){
            pos.set(pos.intValue()+1);
            return new Token(Type.SUB);
        } else if (s.charAt(pos.intValue()) == '*'){
            pos.set(pos.intValue()+1);
            return new Token(Type.MUL);
        } else if (s.charAt(pos.intValue()) == '/'){
            pos.set(pos.intValue()+1);
            return new Token(Type.DIV);
        } else if (s.charAt(pos.intValue()) <= '9' && s.charAt(pos.intValue()) >= '0'){
            String numstr = "";
            //System.out.println(s + " " + pos.intValue());
            while (pos.intValue() < s.length() && ((s.charAt(pos.intValue()) <= '9' && s.charAt(pos.intValue()) >= '0') || s.charAt(pos.intValue()) == '.')){
                numstr += s.charAt(pos.intValue());
                pos.set(pos.intValue()+1);
            }
            return new Token(Type.NUM, Float.parseFloat(numstr));
        } else {
            throw new UnexpectedTokenException(pos.intValue(), "expression", s.substring(pos.intValue(), pos.intValue()+1));
        }
    }

    static Node parseNumber(String s, AtomicInteger pos){
        //System.out.println("parseNumberBegin : " + s + " " + pos);
        int br = 0;
        int beginIdx = pos.intValue();
        //Token nxt;
        do {
            nxt = nextToken(s, pos);
            if (nxt.tp == Type.OPEN_BR){
                br++;
            } else if (nxt.tp == Type.CLOSING_BR){
                br--;
                if (br < 0){
                    throw new UnexpectedTokenException(pos.intValue(), "number or '('", "')'");
                }
            } else if (br == 0 && nxt.tp == Type.NUM){
                return new Node(Type.NUM, null, null, nxt.val);
            } else if (br == 0) {
                if (br > 0){
                    throw new UnexpectedTokenException(pos.intValue(), "number or '(' or ')'", nxt.tp.toString());
                } else {
                    throw new UnexpectedTokenException(pos.intValue(), "number or '('", nxt.tp.toString());
                }
            }
        } while (br != 0 && nxt.tp != Type.END);
        if (br == 0) {
            //System.out.println("parseNumberEnd: " + s + " " + (pos.intValue()-1) + " " + beginIdx);
            return parseAddSub(s, beginIdx + 1, pos.intValue() - 1);
        } else {
            throw new UnexpectedTokenException(pos.intValue(), ")", nxt.tp.toString());
        }
    }
    static Node parseMulDiv(String s, AtomicInteger pos, int endIdx){
        //System.out.println("parseMulDivBegin : " + s + " " + pos + " " + endIdx);
        Node expr = parseNumber(s, pos);
        if (pos.intValue() < endIdx){
            nxt = nextToken(s, pos);
        }
        //System.out.println(nxt.tp);
        while (nxt.tp == Type.MUL || nxt.tp == Type.DIV){
            Type curType = nxt.tp;
            Node next = parseNumber(s, pos);
            expr = new Node(curType, expr, next);
            if (pos.intValue() < endIdx){
                nxt = nextToken(s, pos);
            } else {
                break;
            }
            //System.out.println(nxt.tp);
        }
        if (nxt.tp == Type.ADD || nxt.tp == Type.SUB || nxt.tp == Type.END || (nxt.tp == Type.NUM && pos.intValue() >= endIdx)){
            return expr;
        } else {
            throw new UnexpectedTokenException(pos.intValue(), "+, - or END", nxt.tp.toString());
        }
    }
    static Node parseAddSub(String s, int beginIdx, int endIdx){
        //System.out.println("parseAddSubBegin : " + s + " " + beginIdx + " " + endIdx);
        if (beginIdx == endIdx){
            throw new UnexpectedTokenException(beginIdx, "expression", "");
        }
        AtomicInteger pos = new AtomicInteger(beginIdx);
        nxt = new Token();
        Node expr = parseMulDiv(s, pos, endIdx);
        //System.out.println(pos);
        //System.out.println(nxt.tp);
        while ((nxt.tp == ADD || nxt.tp == Type.SUB) && pos.intValue() <= endIdx){
            Type curType = nxt.tp;
            Node next = parseMulDiv(s, pos, endIdx);
            expr = new Node(curType, expr, next);
        }
        //System.out.println("parseAddSubEnd : " + s + " " + beginIdx + " " + endIdx);
        return expr;
    }


    public static float calculate(String s){
        s = s.replace(" ", "");
        Node tree = parseAddSub(s, 0, s.length());
        //tree.print(tree, 1);
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