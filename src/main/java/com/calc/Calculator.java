package com.calc;

import java.util.Objects;
import java.util.logging.*;

import static com.calc.Type.*;

public class Calculator {

    private static final Logger log = Logger.getLogger(Calculator.class.getName());


    private final Variables variables;

    Calculator(){
        variables = new Variables();
        try {
            LogManager.getLogManager().reset();
            FileHandler fh = new FileHandler("C:/Users/mmanv/Documents/GitHub/calculator/CalculatorLog.log");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node parseNumber(Lexer lex){
        log.info("parseNumberBegin : " + lex.getS() + " " + lex.getPos());
        int br = 0;
        int beginIdx = lex.getPos();
        Token nxt = lex.nextToken();
        if (nxt.getTp() == Type.SUB){
            return new Node(nxt, new Node(new Token(Type.NUM, 0), null, null), parseNumber(lex));
        } else {
            lex.returnToPrevPos();
        }
        do {
            nxt = lex.nextToken();
            if (nxt.getTp() == Type.OPEN_BR){
                br++;
                beginIdx = lex.getPos();
            } else if (nxt.getTp() == Type.CLOSING_BR){
                br--;
                if (br < 0){
                    throw new UnexpectedTokenException(lex.getS(), lex.getPos()-1,  "')'", "NUM", "(");
                }
            } else if ((br == 0) && ((nxt.getTp() == Type.NUM) || (nxt.getTp() == Type.VAR))){
                if (nxt.getTp() == Type.VAR && Objects.isNull(variables.getValue(nxt.getName()))){
                    throw new UnexpectedTokenException(lex.getS(), lex.getPos()-1, nxt.getName(), "existing variable name");
                }
                return new Node(nxt, null, null);
            } else if (br == 0) {
                throw new UnexpectedTokenException(lex.getS(), lex.getPos()-1, nxt.getTp().toString(), "NUM", "(");
            }
        } while (br != 0 && nxt.getTp() != Type.END);
        if (br == 0) {
            return parseAddSub(new Lexer(lex.getS(), beginIdx), beginIdx, lex.getPos() - 1);
        } else {
            throw new UnexpectedTokenException(lex.getS(), lex.getPos(), nxt.getTp().toString(), ")");
        }
    }
    private Node parseMulDiv(Lexer lex, int endIdx){
        log.info("parseMulDivBegin : " + lex.getS() + " " + lex.getPos() + " " + endIdx);
        Node expr = parseNumber(lex);
        Token nxt = lex.nextToken();
        if (lex.getPos() > endIdx){
            lex.returnToPrevPos();
            nxt = new Token(END);
        }
        while (nxt.getTp() == Type.MUL || nxt.getTp() == Type.DIV){
            Type curType = nxt.getTp();
            Node next = parseNumber(lex);
            expr = new Node(new Token(curType), expr, next);
            nxt = lex.nextToken();
            if (lex.getPos() > endIdx){
                nxt = new Token(END);
                lex.returnToPrevPos();
                break;
            }
        }
        if (nxt.getTp() == Type.ADD || nxt.getTp() == Type.SUB){
            lex.returnToPrevPos();
        } else if (nxt.getTp() != Type.END){
            throw new UnexpectedTokenException(lex.getS(), lex.getPos()-1,  nxt.getTp().toString(), "+", "-", "END");
        }
        return expr;
    }
    private Node parseAddSub(Lexer lex, int beginIdx, int endIdx){
        log.info("parseAddSubBegin : " + lex.getS() + " " + beginIdx + " " + endIdx + lex.getS().substring(beginIdx, endIdx));
        if (beginIdx == endIdx){
            throw new UnexpectedTokenException(lex.getS(), beginIdx, "", "expression");
        }
        Node expr = parseMulDiv(lex, endIdx);
        Token nxt = lex.nextToken();
        while (nxt.getTp() == ADD || nxt.getTp() == Type.SUB){
            Type curType = nxt.getTp();
            Node next = parseMulDiv(lex, endIdx);
            nxt = lex.nextToken();
            if (lex.getPos() > endIdx){
                lex.returnToPrevPos();
            }
            expr = new Node(new Token(curType), expr, next);
        }
        return expr;
    }

    private Node parseVariable(Lexer lex){
        Token varName = lex.nextToken();
        Token nxt = lex.nextToken();
        if (varName.getTp() != Type.VAR || nxt.getTp() != Type.ASS){
            return parseAddSub(new Lexer(lex.getS(), 0), 0, lex.getS().length());
        }
        return new Node(nxt, new Node(varName, null, null), parseAddSub(lex, lex.getPos(), lex.getS().length()));
    }

    public EvalResult calculate(String s){
        Lexer lex = new Lexer(s, 0);
        Eval e = new Eval();
        Node tree = parseVariable(lex);
        return e.eval(tree, variables);
    }
}

