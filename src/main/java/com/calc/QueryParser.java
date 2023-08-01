package com.calc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class QueryParser {

    private String getCommand(String s){
        if (s.indexOf('#') != -1){
            return s.substring(0, s.lastIndexOf('#'));
        }
        return "eval";
    }

    private String getExpression(String s){
        if (s.indexOf('#') != -1){
            int offset = s.lastIndexOf('#')+1;
            char[] charArray = new char[offset];
            Arrays.fill(charArray, ' ');
            return new String(charArray)+s.substring(s.lastIndexOf('#')+1);
        }
        return s;
    }

    public Command parse(String s) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        CommandParser commandParser = new CommandParser();
        Class<? extends Command> X = commandParser.parse(getCommand(s));
        MathExpressionParser expressionParser = new MathExpressionParser();
        Node expression = expressionParser.parse(getExpression(s));
        Constructor<? extends Command> constructor = X.getConstructor(Node.class);
        return constructor.newInstance(expression);
    }

}
