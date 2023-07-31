package com.calc;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MathExpressionParser expressionParser = new MathExpressionParser();
        CommandParser commandParser = new CommandParser();
        RunCommand run = new RunCommand();
        Variables variables = new Variables();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            try {
                ParseQuery parser = new ParseQuery();
                Node expression = expressionParser.parse(parser.getExpression(s));
                Command command = commandParser.parse(parser.getCommand(s));
                Result res = run.runCommand(command, expression, variables);
                Output.printResult(res);
            } catch (UnexpectedTokenException | UnexpectedVariableException e) {
                Output.printPositionException(e);
            } catch (Exception e){
                Output.printException(e);
            }
        }
    }
}

