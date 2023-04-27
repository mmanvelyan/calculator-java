package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.calc.Calculator.calculate;

public class calculateTest {

    @Test
    public void add() {
        Assertions.assertEquals(5.0, calculate("2+3"));
    }
    @Test
    public void mult() {
        Assertions.assertEquals(6.0, calculate("2*3"));
    }
    @Test
    public void sub() {
        Assertions.assertEquals(-1.0, calculate("2-3"));
    }
    @Test
    public void div() {
        Assertions.assertEquals(1.5, calculate("3/2"));
    }

    @Test
    public void divBy0() {
        Assertions.assertThrows(ArithmeticException.class, () -> calculate("1/0"));
    }

    @Test
    public void missingClosingBracket() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("(2+3"));
    }

    @Test
    public void missingOpenBracket() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("(2+3))"));
    }


    @Test
    public void brackets() {
        Assertions.assertEquals(5.0, calculate("(2+3)"));
    }

    @Test
    public void emptyBrackets() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("()"));
    }

    @Test
    public void divOrder() {
        Assertions.assertEquals(0.25, calculate("1/2/2"));
    }

    @Test
    public void subOrder() {
        Assertions.assertEquals(0.0, calculate("5-2-3"));
    }

    @Test
    public void multAddOrder() {
        Assertions.assertEquals(6.0, calculate("2+2*2"));
    }

    @Test
    public void multSubOrder() {
        Assertions.assertEquals(-2.0, calculate("2-2*2"));
    }

    @Test
    public void multBracketsOrder() {
        Assertions.assertEquals(8.0, calculate("(2+2)*2"));
    }

    @Test
    public void calculateTestMixed() {
        Assertions.assertEquals(36.0, calculate("( 2+ 7) * 3 + 9"));
        Assertions.assertEquals(25.0, calculate("42/6+(70-64)*5+52/26-70/5"));
        Assertions.assertEquals(93.0, calculate("70-(81-39)/7+6*7-90/5+85/17"));
        Assertions.assertEquals(-210.0, calculate("(900-250+140)-(400+900/3-200)-500"));
    }

}