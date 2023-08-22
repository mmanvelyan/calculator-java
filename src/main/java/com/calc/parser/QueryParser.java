package com.calc.parser;

import com.calc.command.NodeVisitor;
import com.calc.node.Node;

import java.util.Arrays;

public class QueryParser {

    private String fixPosition(String s, int offset){
        char[] charArray = new char[offset];
        Arrays.fill(charArray, ' ');
        return new String(charArray)+s;
    }

    public Query parse(String s) {

        int separatorPosition = s.indexOf('#');
        String commandPart = separatorPosition != -1 ? s.substring(0, separatorPosition) : "eval";
        String expressionPart = separatorPosition != -1 ? s.substring(separatorPosition+1) : s;
        String fixedExpressionPart = fixPosition(expressionPart, separatorPosition+1);

        CommandParser commandParser = new CommandParser();
        NodeVisitor command = commandParser.parse(commandPart);
        MathExpressionParser expressionParser = new MathExpressionParser();
        Node expression = expressionParser.parse(fixedExpressionPart);
        return new Query(command, expression);
    }

}
