package com.calc;

import com.calc.command.FunctionCycleException;
import com.calc.command.UnexpectedFunctionException;
import com.calc.command.UnexpectedVariableException;
import com.calc.parser.Query;
import com.calc.parser.QueryParser;
import com.calc.lexer.UnexpectedTokenException;

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
                Output.printResult(query.getExpression().accept(query.getCommand(), variables, functions));
            } catch (UnexpectedTokenException | UnexpectedVariableException | UnexpectedFunctionException |
                     FunctionCycleException e) {
                Output.printPositionException(e);
            } catch (Exception e){
                Output.printException(e);
            }
        }
    }

}

