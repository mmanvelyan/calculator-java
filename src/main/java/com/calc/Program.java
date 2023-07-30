package com.calc;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MathExpressionParser calc = new MathExpressionParser();
        Variables variables = new Variables();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            try {
                Node tree = calc.parse(s);
                Eval e = new Eval();
                EvalResult res = e.eval(tree, variables);
                Output.printResult(res);
            } catch (UnexpectedTokenException | UnexpectedVariableException e) {
                Output.printPositionException(e);
            } catch (Exception e){
                Output.printException(e);
            }
        }
    }
}

