package com.calc;

import java.util.Scanner;
import java.util.logging.*;


import static com.calc.Type.*;

public class Calculator {

    private static Logger log = Logger.getLogger(Calculator.class.getName());

    Token nxt;
    Variables variables;

    Calculator(){
        variables = new Variables();
    }

    Node parseNumber(Lexer lex){
        log.info("parseNumberBegin : " + lex.s + " " + lex.pos);
        int br = 0;
        int beginIdx = lex.pos;
        nxt = lex.nextToken();
        if (nxt.tp == Type.SUB){
            return new Node(nxt, new Node(new Token(Type.NUM, 0), null, null), parseNumber(lex));
        } else {
            lex.prevToken();
        }
        do {
            nxt = lex.nextToken();
            if (nxt.tp == Type.OPEN_BR){
                br++;
            } else if (nxt.tp == Type.CLOSING_BR){
                br--;
                if (br < 0){
                    throw new UnexpectedTokenException(lex.pos-1,  "')'", "NUM", "(");
                }
            } else if ((br == 0) && ((nxt.tp == Type.NUM) || (nxt.tp == Type.VAR))){
                return new Node(nxt, null, null);
            } else if (br == 0) {
                throw new UnexpectedTokenException(lex.pos-1, nxt.tp.toString(), "NUM", "(");
            }
        } while (br != 0 && nxt.tp != Type.END);
        if (br == 0) {
            return parseAddSub(new Lexer(lex), beginIdx + 1, lex.pos - 1);
        } else {
            throw new UnexpectedTokenException(lex.pos, nxt.tp.toString(), ")");
        }
    }
    Node parseMulDiv(Lexer lex, int endIdx){
        log.info("parseMulDivBegin : " + lex.s + " " + lex.pos + " " + endIdx);
        Node expr = parseNumber(lex);
        nxt = lex.nextToken();
        if (lex.pos > endIdx){
            lex.prevToken();
            nxt = new Token(END);
        }
        while (nxt.tp == Type.MUL || nxt.tp == Type.DIV){
            Type curType = nxt.tp;
            Node next = parseNumber(lex);
            expr = new Node(new Token(curType), expr, next);
            nxt = lex.nextToken();
            if (lex.pos > endIdx){
                nxt = new Token(END);
                lex.prevToken();
                break;
            }
        }
        if (nxt.tp == Type.ADD || nxt.tp == Type.SUB){
            lex.prevToken();
        } else if (nxt.tp != Type.END){
            throw new UnexpectedTokenException(lex.pos-1,  nxt.tp.toString(), "+", "-", "END");
        }
        return expr;
    }
    Node parseAddSub(Lexer lex, int beginIdx, int endIdx){
        log.info("parseAddSubBegin : " + lex.s + " " + beginIdx + " " + endIdx);
        if (beginIdx == endIdx){
            throw new UnexpectedTokenException(beginIdx, "", "expression");
        }
        lex.pos = beginIdx;
        nxt = new Token();
        Node expr = parseMulDiv(lex, endIdx);
        nxt = lex.nextToken();
        while (nxt.tp == ADD || nxt.tp == Type.SUB){
            Type curType = nxt.tp;
            Node next = parseMulDiv(lex, endIdx);
            nxt = lex.nextToken();
            if (lex.pos > endIdx){
                lex.prevToken();
            }
            expr = new Node(new Token(curType), expr, next);
        }
        return expr;
    }

    Node parseVariable(Lexer lex){
        Token varName = lex.nextToken();
        nxt = lex.nextToken();
        if (varName.tp != Type.VAR || nxt.tp != Type.EQ){
            return parseAddSub(new Lexer(lex.s, 0), 0, lex.s.length());
        }
        return new Node(nxt, new Node(varName, null, null), parseAddSub(lex, lex.pos, lex.s.length()));
    }

    public float calculate(String s){
        Lexer lex = new Lexer(s, 0);
        Eval e = new Eval();
        Node tree = parseVariable(lex);
        return e.eval(tree, variables);
    }
}

class Program{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Calculator calc = new Calculator();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            System.out.println(calc.calculate(s));
        }
    }
}