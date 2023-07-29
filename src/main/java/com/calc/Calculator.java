package com.calc;

import java.util.ArrayList;

import static com.calc.Type.*;

/*
<expression> -> <variable> <"="> <expression>
<expression> -> <term>
<term> -> <factor> <"+"> <term>
<term> -> <factor> <"-"> <term>
<term> -> <factor>
<factor> -> <number> <"*"> <factor>
<factor> -> <number> <"/"> <factor>
<factor> -> <number>
<number> -> <numeric>
<number> -> <variable>
<number> -> <"("> <expression> <")">
 */

public class Calculator {

    private final Variables variables = new Variables();

    private Node parseNumber(PushBackLexer lex){
        Token nxt = lex.nextToken();
        if (nxt.getType() == SUB){
            return new Node(nxt, new Node(new Token(0), null, null), parseNumber(lex));
        } else if (nxt.getType() == OPEN_BR){
            Node expression = parseExpression(lex);
            nxt = lex.nextToken();
            if (nxt.getType() != CLOSING_BR){
                throw new UnexpectedTokenException(nxt, ")");
            }
            return expression;
        } else if (nxt.getType() == NUM || nxt.getType() == VAR) {
            return new Node(nxt, null, null);
        } else {
            throw new UnexpectedTokenException(nxt, "(", "NUM", "VAR");
        }
    }
    private Node parseFactor(PushBackLexer lex){
        Node factor = parseNumber(lex);
        Token nxt = lex.nextToken();
        while (nxt.getType() == MUL || nxt.getType() == DIV){
            Node number = parseNumber(lex);
            factor = new Node(nxt, factor, number);
            nxt = lex.nextToken();
            if (nxt.getType() == ADD || nxt.getType() == SUB || nxt.getType() == ASS || nxt.getType() == CLOSING_BR){
                lex.returnToPrevPos();
                return factor;
            }
        }
        if (nxt.getType() == ADD || nxt.getType() == SUB || nxt.getType() == ASS || nxt.getType() == CLOSING_BR){
            lex.returnToPrevPos();
            return factor;
        } else if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "MUL", "DIV");
        }
        return factor;
    }

    private Node parseTerm(PushBackLexer lex){
        Node term = parseFactor(lex);
        Token nxt = lex.nextToken();
        while (nxt.getType() == ADD || nxt.getType() == SUB){
            Node factor = parseFactor(lex);
            term = new Node(nxt, term, factor);
            nxt = lex.nextToken();
            if (nxt.getType() == ASS || nxt.getType() == CLOSING_BR){
                lex.returnToPrevPos();
                return term;
            }
        }
        if (nxt.getType() == ASS || nxt.getType() == CLOSING_BR){
            lex.returnToPrevPos();
            return term;
        } else if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "ADD", "SUB");
        }
        return term;
    }

    private Node parseExpression(PushBackLexer lex){
        ArrayList<Node> terms = new ArrayList<>();
        terms.add(parseTerm(lex));
        Token nxt = lex.nextToken();
        boolean flag = (terms.get(0).getToken().getType() != VAR);
        while (nxt.getType() == Type.ASS){
            if (flag){
                throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV", "END");
            }
            Node term = parseTerm(lex);
            terms.add(term);
            flag = (terms.get(terms.size()-1).getToken().getType() != VAR);
            nxt = lex.nextToken();
            if (nxt.getType() == CLOSING_BR){
                lex.returnToPrevPos();
                return new Node(terms, new Token(ASS));
            }
        }
        if (nxt.getType() == CLOSING_BR){
            lex.returnToPrevPos();
            return new Node(terms, new Token(ASS));
        } else if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "END");
        }
        return new Node(terms, new Token(ASS));
    }

    private Node parse(String s){
        PushBackLexer lex = new PushBackLexer(new BaseLexer(s));
        Node tree = parseExpression(lex);
        Token nxt = lex.nextToken();
        if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "END");
        }
        return tree;
    }

    public EvalResult calculate(String s){
        Node tree = parse(s);
        Eval e = new Eval();
        return e.eval(tree, variables);
    }
}

