package com.calc;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        QueryParser parser = new QueryParser();
        Variables variables = new Variables();
        Functions functions = new Functions();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            try {
                Query query = parser.parse(s);
                Output.printResult(query.getCommand().execute(query.getExpression(), variables, functions));
            } catch (UnexpectedTokenException | UnexpectedVariableException | UnexpectedFunctionException | UnexpectedArgumentException | FunctionCycleException e) {
                Output.printPositionException(e);
            } catch (Exception e){
                Output.printException(e);
            }
        }
    }
}

