package com.calc;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Calculator calc = new Calculator();
        while (in.hasNextLine()) {
            String s = in.nextLine();
            Output.printResult(calc.calculate(s));
        }
    }
}

