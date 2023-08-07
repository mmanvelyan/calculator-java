package com.calc;

import java.util.ArrayList;

import static com.calc.Type.*;

/*
<query> -> <command> # <expression>
<query> -> <expression>
<expression> -> <variable> <"="> <expression>
<expression> -> <function> <"="> <term>
<expression> -> <term>
<term> -> <factor> <"+"> <term>
<term> -> <factor> <"-"> <term>
<term> -> <factor>
<factor> -> <number> <"*"> <factor>
<factor> -> <number> <"/"> <factor>
<factor> -> <number>
<number> -> <numeric>
<number> -> <variable>
<number> -> <function>
<number> -> <"("> <expression> <")">
<functionVal> -> <variable> ( <functionValArgs> )
<functionAss> -> <variable> ( <functionAssArgs> )
<functionValArgs> -> <expression> , <functionValArgs>
<functionValArgs> -> <expression>
<functionAssArgs> -> <variable> , <functionAssArgs>
<functionAssArgs> -> <variable>
 */

public class MathExpressionParser {

    private ArrayList<Node> parseArgs(PushBackLexer lex) {
        ArrayList<Node> args = new ArrayList<>();
        Node arg = parseExpression(lex);
        args.add(arg);
        Token nxt = lex.nextToken();
        while (nxt.getType() == COMMA){
            arg = parseExpression(lex);
            args.add(arg);
            nxt = lex.nextToken();
            if (nxt.getType() == CLOSING_BR){
                lex.returnToPrevPos();
                return args;
            } else if (nxt.getType() != COMMA){
                throw new UnexpectedTokenException(nxt, ",");
            }
        }
        if (nxt.getType() == CLOSING_BR){
            lex.returnToPrevPos();
            return args;
        } else {
            throw new UnexpectedTokenException(nxt, ")", ",");
        }
    }

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
        } else if (nxt.getType() == NUM) {
            return new Node(nxt, null, null);
        } else if (nxt.getType() == VAR) {
                Token nxtFun = lex.nextToken();
                if (nxtFun.getType() == OPEN_BR){
                    ArrayList<Node> args = parseArgs(lex);
                    nxtFun = lex.nextToken();
                    if (nxtFun.getType() == CLOSING_BR){
                        return new Node(new Token(nxt.getName(), args, nxt.getPos()), null, null);
                    } else {
                        throw new UnexpectedTokenException(nxt, ")");
                    }
                } else {
                    lex.returnToPrevPos();
                    return new Node(nxt, null, null);
                }
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
            if (nxt.getType() == ADD || nxt.getType() == SUB || nxt.getType() == ASS || nxt.getType() == CLOSING_BR || nxt.getType() == COMMA){
                lex.returnToPrevPos();
                return factor;
            }
        }
        if (nxt.getType() == ADD || nxt.getType() == SUB || nxt.getType() == ASS || nxt.getType() == CLOSING_BR || nxt.getType() == COMMA){
            lex.returnToPrevPos();
            return factor;
        } else if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV");
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
            if (nxt.getType() == ASS || nxt.getType() == CLOSING_BR || nxt.getType() == COMMA){
                lex.returnToPrevPos();
                return term;
            }
        }
        if (nxt.getType() == ASS || nxt.getType() == CLOSING_BR || nxt.getType() == COMMA){
            lex.returnToPrevPos();
            return term;
        } else if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV");
        }
        return term;
    }

    private Node parseExpression(PushBackLexer lex){
        ArrayList<Node> terms = new ArrayList<>();
        terms.add(parseTerm(lex));
        Token nxt = lex.nextToken();
        boolean wasTerm = (terms.get(0).getToken().getType() != VAR) && (terms.get(0).getToken().getType() != FUN);
        boolean wasFunction = terms.get(0).getToken().getType() == FUN;
        boolean wasVariable = terms.get(0).getToken().getType() == VAR;
        while (nxt.getType() == Type.ASS){
            if (wasTerm || (wasFunction && wasVariable)){
                throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV", "END");
            }
            Node term = parseTerm(lex);
            terms.add(term);
            wasTerm = wasTerm || wasFunction || (terms.get(terms.size()-1).getToken().getType() != VAR) && (terms.get(terms.size()-1).getToken().getType() != FUN);
            wasFunction = wasFunction || terms.get(terms.size()-1).getToken().getType() == FUN;
            wasVariable = wasVariable || terms.get(terms.size()-1).getToken().getType() == VAR;
            nxt = lex.nextToken();
            if (nxt.getType() == CLOSING_BR || nxt.getType() == COMMA){
                lex.returnToPrevPos();
                return new Node(terms, new Token(ASS));
            }
        }
        if (nxt.getType() == CLOSING_BR || nxt.getType() == COMMA){
            lex.returnToPrevPos();
            return new Node(terms, new Token(ASS));
        } else if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "END");
        }
        return new Node(terms, new Token(ASS));
    }

    public Node parse(String s){
        PushBackLexer lex = new PushBackLexer(new BaseLexer(s));
        Node tree = parseExpression(lex);
        Token nxt = lex.nextToken();
        if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "END");
        }
        return tree;
    }
}

