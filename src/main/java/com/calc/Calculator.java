package com.calc;

import java.util.Scanner;

import static com.calc.Type.*;

public class Calculator {

    static Token nxt;

    static Node parseNumber(Lexer lex){
        //System.out.println("parseNumberBegin : " + s + " " + pos);
        int br = 0;
        int beginIdx = lex.pos;
        nxt = lex.nextToken();
        if (nxt.tp == Type.SUB){
            return new Node(nxt, new Node(new Token(Type.NUM, 0), null, null), parseNumber(lex));
        } else {
            lex.prevToken();
        }
        //Token nxt;
        do {
            nxt = lex.nextToken();
            //System.out.println(nxt.tp+" "+ nxt.val);
            if (nxt.tp == Type.OPEN_BR){
                br++;
            } else if (nxt.tp == Type.CLOSING_BR){
                br--;
                if (br < 0){
                    throw new UnexpectedTokenException(lex.pos,  "')'", "NUM", "(");
                }
            } else if (br == 0 && nxt.tp == Type.NUM){
                return new Node(nxt, null, null);
            } else if (br == 0) {
                throw new UnexpectedTokenException(lex.pos, nxt.tp.toString(), "NUM", "(");
            }
        } while (br != 0 && nxt.tp != Type.END);
        if (br == 0) {
            //System.out.println("parseNumberEnd: " + s + " " + (pos.intValue()-1) + " " + beginIdx);
            return parseAddSub(lex, beginIdx + 1, lex.pos - 1);
        } else {
            throw new UnexpectedTokenException(lex.pos, nxt.tp.toString(), ")");
        }
    }
    static Node parseMulDiv(Lexer lex, int endIdx){
        //System.out.println("parseMulDivBegin : " + s + " " + pos + " " + endIdx);
        Node expr = parseNumber(lex);
        if (lex.pos < endIdx){
            nxt = lex.nextToken();
        } else {
            nxt = new Token(END);
        }
        //System.out.println(nxt.tp);
        while (nxt.tp == Type.MUL || nxt.tp == Type.DIV){
            Type curType = nxt.tp;
            Node next = parseNumber(lex);
            expr = new Node(new Token(curType), expr, next);
            if (lex.pos < endIdx){
                nxt = lex.nextToken();
            } else {
                nxt = new Token(END);
                break;
            }
            //System.out.println(nxt.tp);
        }
        if (nxt.tp == Type.ADD || nxt.tp == Type.SUB){
            lex.prevToken();
        } else if (nxt.tp != Type.END){
            throw new UnexpectedTokenException(lex.pos,  nxt.tp.toString(), "+", "-", "END");
        }
        return expr;
    }
    static Node parseAddSub(Lexer lex, int beginIdx, int endIdx){
        //System.out.println("parseAddSubBegin : " + s + " " + beginIdx + " " + endIdx);
        if (beginIdx == endIdx){
            throw new UnexpectedTokenException(beginIdx, "", "expression");
        }
        lex.pos = beginIdx;
        nxt = new Token();
        Node expr = parseMulDiv(lex, endIdx);
        //System.out.println(pos);
        //System.out.println(nxt.tp);
        nxt = lex.nextToken();
        while ((nxt.tp == ADD || nxt.tp == Type.SUB) && lex.pos <= endIdx){
            Type curType = nxt.tp;
            Node next = parseMulDiv(lex, endIdx);
            nxt = lex.nextToken();
            expr = new Node(new Token(curType), expr, next);
        }
        //System.out.println("parseAddSubEnd : " + s + " " + beginIdx + " " + endIdx);
        return expr;
    }


    public static float calculate(String s){
        s = s.replace(" ", "");
        Lexer lex = new Lexer(s, 0);
        Node tree = parseAddSub(lex, 0, s.length());
        //tree.print(tree, 1);
        return tree.eval();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String s = in.nextLine();
            System.out.println(calculate(s));
        }
    }
}