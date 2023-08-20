package com.calc.parser;

import com.calc.lexer.*;
import com.calc.node.*;

import java.util.ArrayList;

import static com.calc.lexer.Type.*;

/*
<query> -> <command> # <expression>
<query> -> <expression>

<statement> -> <term>
<statement> -> <definition> = <statement>
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

    private ArrayList<Node> parseArgs(PushBackLexer lex) {
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

    private Node parseNumber(PushBackLexer lex){
        Token nxt = lex.nextToken();
        if (nxt.getType() == SUB){
            return new BinaryOperatorNode(SUB, new NumberNode(0), parseNumber(lex));
        } else if (nxt.getType() == OPEN_BR){
            Node expression = parseTerm(lex);
            nxt = lex.nextToken();
            if (nxt.getType() != CLOSING_BR){
                throw new UnexpectedTokenException(nxt, ")");
            }
            return expression;
        } else if (nxt.getType() == NUM) {
            return new NumberNode(nxt.getVal());
        } else if (nxt.getType() == NAME) {
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

    private Node parsePower(PushBackLexer lex){
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

    private Node parseFactor(PushBackLexer lex){
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

    private Node parseTerm(PushBackLexer lex){
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

    private Node parseStatement(PushBackLexer lex){

        int tokenCount = 0;
        Token nxt = lex.nextToken();
        tokenCount++;
        if (nxt.getType() != NAME){
            lex.returnToPrevPos();
            return parseTerm(lex);
        }
        String name = nxt.getName();

        nxt = lex.nextToken();
        tokenCount++;
        if (nxt.getType() == OPEN_BR){
            ArrayList<String> argNames = new ArrayList<>();
            nxt = lex.nextToken();
            tokenCount++;
            while (nxt.getType() == NAME){
                argNames.add(nxt.getName());
                nxt = lex.nextToken();
                tokenCount++;
                if (nxt.getType() == CLOSING_BR) {
                    break;
                } else if (nxt.getType() == COMMA){
                    nxt = lex.nextToken();
                    tokenCount++;
                } else {
                    lex.returnToPrevPos(tokenCount);
                    return parseTerm(lex);
                }
            }
            if (nxt.getType() != CLOSING_BR){
                lex.returnToPrevPos(tokenCount);
                return parseTerm(lex);
            }
            nxt = lex.nextToken();
            tokenCount++;
            if (nxt.getType() == ASS){
                Node statement = parseStatement(lex);
                return new DefineNode(name, argNames, statement);
            } else {
                lex.returnToPrevPos(tokenCount);
                return parseTerm(lex);
            }
        } else {
            if (nxt.getType() == ASS){
                Node statement = parseStatement(lex);
                return new DefineNode(name, statement);
            } else {
                lex.returnToPrevPos(tokenCount);
                return parseTerm(lex);
            }
        }
    }

    public Node parse(String s){
        PushBackLexer lex = new PushBackLexer(new BaseLexer(s), Integer.MAX_VALUE);
        Node tree = parseStatement(lex);
        Token nxt = lex.nextToken();
        if (nxt.getType() != END){
            throw new UnexpectedTokenException(nxt, "END");
        }
        return tree;
    }
}

