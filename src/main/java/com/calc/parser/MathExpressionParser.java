package com.calc.parser;

import com.calc.lexer.*;
import com.calc.node.*;

import java.util.ArrayList;

import static com.calc.lexer.Type.*;

/*
<query> -> <command> # <expression>
<query> -> <expression>

<statement> -> <term>
<statement> -> <definition> = <term>
<term> -> <factor> + <term>
<term> -> <factor> - <term>
<term> -> <factor>
<factor> -> <power> * <factor>
<factor> -> <power> / <factor>
<factor> -> <power>
<power> -> <number> ^ <power>
<power> -> <number>
<number> -> <numeric>
<number> -> <variable>
<number> -> <function>
<number> -> ( <expression> )
<functionVal> -> <variable> ( <functionValArgs> )
<functionAss> -> <variable> ( <functionAssArgs> )
<functionValArgs> -> <expression> , <functionValArgs>
<functionValArgs> -> <expression>
<functionAssArgs> -> <variable> , <functionAssArgs>
<functionAssArgs> -> <variable>
 */

public class MathExpressionParser {

    private ArrayList<Node> parseArgs(BookmarkLexer lex) {
        ArrayList<Node> args = new ArrayList<>();
        Node arg = parseTerm(lex);
        args.add(arg);
        Token nxt = lex.nextToken();
        while (nxt.getType() == COMMA){
            arg = parseTerm(lex);
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

    private Node parseNumber(BookmarkLexer lex){
        Token nxt = lex.nextToken();
        Type type = nxt.getType();
        if (type == SUB){
            return new BinaryOperatorNode(SUB, new NumberNode(0), parseNumber(lex));
        } else if (type == OPEN_BR){
            Node expression = parseTerm(lex);
            nxt = lex.nextToken();
            if (nxt.getType() != CLOSING_BR){
                throw new UnexpectedTokenException(nxt, ")");
            }
            return expression;
        } else if (type == NUM) {
            return new NumberNode(nxt.getVal());
        } else if (type == NAME) {
            Token nxtFun = lex.nextToken();
            if (nxtFun.getType() == OPEN_BR){
                ArrayList<Node> args = parseArgs(lex);
                nxtFun = lex.nextToken();
                if (nxtFun.getType() == CLOSING_BR){
                    return new FunctionCallNode(args, nxt.getName(), nxt.getPos());
                } else {
                    throw new UnexpectedTokenException(nxt, ")");
                }
            } else {
                lex.returnToPrevPos();
                return new VariableNode(nxt.getName(), nxt.getPos());
            }
        } else {
            throw new UnexpectedTokenException(nxt, "(", "NUM", "VAR");
        }
    }

    private Node parsePower(BookmarkLexer lex){
        Node number = parseNumber(lex);
        Token nxt = lex.nextToken();
        Type tokenType = nxt.getType();
        if (tokenType == POWER){
            Node power = parsePower(lex);
            number = new BinaryOperatorNode(POWER, number, power);
        } else if (tokenType == CLOSING_BR || tokenType == COMMA || tokenType == ADD || tokenType == SUB ||
                tokenType == MUL || tokenType == DIV || tokenType == ASS){
            lex.returnToPrevPos();
            return number;
        } else if (tokenType != END){
            throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV", "POWER");
        }
        return number;
    }

    private Node parseFactor(BookmarkLexer lex){
        Node factor = parsePower(lex);
        Token nxt = lex.nextToken();
        while (nxt.getType() == MUL || nxt.getType() == DIV){
            Node power = parsePower(lex);
            factor = new BinaryOperatorNode(nxt.getType(), factor, power);
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
            throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV", "POWER");
        }
        return factor;
    }

    private Node parseTerm(BookmarkLexer lex){
        Node term = parseFactor(lex);
        Token nxt = lex.nextToken();
        while (nxt.getType() == ADD || nxt.getType() == SUB){
            Node factor = parseFactor(lex);
            term = new BinaryOperatorNode(nxt.getType(), term, factor);
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
            throw new UnexpectedTokenException(nxt, "ADD", "SUB", "MUL", "DIV", "POWER");
        }
        return term;
    }

    private Node parseStatement(BookmarkLexer lex){

        lex.setBookmark();
        Token nxt = lex.nextToken();
        if (nxt.getType() != NAME){
            lex.returnToPrevPos();
            return parseTerm(lex);
        }
        String name = nxt.getName();

        nxt = lex.nextToken();
        if (nxt.getType() == OPEN_BR){
            ArrayList<String> argNames = new ArrayList<>();
            nxt = lex.nextToken();
            while (nxt.getType() == NAME){
                argNames.add(nxt.getName());
                nxt = lex.nextToken();
                if (nxt.getType() == CLOSING_BR) {
                    break;
                } else if (nxt.getType() == COMMA){
                    nxt = lex.nextToken();
                } else {
                    lex.returnToBookmark();
                    return parseTerm(lex);
                }
            }
            if (nxt.getType() != CLOSING_BR){
                lex.returnToBookmark();
                return parseTerm(lex);
            }
            nxt = lex.nextToken();
            if (nxt.getType() == ASS){
                Node term = parseTerm(lex);
                return new DefineNode(name, argNames, term);
            } else {
                lex.returnToBookmark();
                return parseTerm(lex);
            }
        } else {
            if (nxt.getType() == ASS){
                Node term = parseTerm(lex);
                return new DefineNode(name, term);
            } else {
                lex.returnToBookmark();
                return parseTerm(lex);
            }
        }
    }

    public Node parse(String s){
        BookmarkLexer lex = new BookmarkLexer(new BaseLexer(s));
        Node tree = parseStatement(lex);
        Token nxt = lex.nextToken();
        if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "END");
        }
        return tree;
    }
}

