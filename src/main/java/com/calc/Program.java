package com.calc;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Calculator calc = new Calculator();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            try {
                Output.printResult(calc.calculate(s));
            } catch (UnexpectedTokenException e) {
                int pos = e.getPos();
                for (int i = 0; i < pos; i++) {
                    System.out.print(" ");
                }
                System.out.println("^");
                System.out.println(e.getMessage());
            } catch (UnexpectedVariableException e){
                int pos = e.getPos();
                for (int i = 0; i < pos; i++) {
                    System.out.print(" ");
                }
                System.out.println("^");
                System.out.println(e.getMessage());
            } catch (ArithmeticException e){
                System.out.println(e.getMessage());
            } catch (RollbackLevelException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

