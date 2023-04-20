package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.calc.Main.calculate;

public class MainTest {

    @Test
    public void calculateTestAdd() {
        Assertions.assertEquals(5.0, calculate("2+3"));
    }
    @Test
    public void calculateTestMult() {
        Assertions.assertEquals(6.0, calculate("2*3"));
    }
    @Test
    public void calculateTestSub() {
        Assertions.assertEquals(-1.0, calculate("2-3"));
    }
    @Test
    public void calculateTestDiv() {
        Assertions.assertEquals(1.5, calculate("3/2"));
    }

    @Test
    public void calculateTestDivBy0() {
        Assertions.assertThrows(ArithmeticException.class, () -> calculate("1/0"));
    }

    @Test
    public void calculateTestWrongBrackets() {
        Assertions.assertThrows(BracketsException.class, () -> calculate("(2+3"));
        Assertions.assertThrows(BracketsException.class, () -> calculate("(2+3))"));
        Assertions.assertThrows(BracketsException.class, () -> calculate("2+3)"));
    }

    @Test
    public void calculateTestBrackets() {
        Assertions.assertEquals(5.0, calculate("(2+3)"));
    }

    @Test
    public void calculateTestMixed() {
        Assertions.assertEquals(36.0, calculate("( 2+ 7) * 3 + 9"));
        Assertions.assertEquals(25.0, calculate("42/6+(70-64)*5+52/26-70/5"));
        Assertions.assertEquals(93.0, calculate("70-(81-39)/7+6*7-90/5+85/17"));
        Assertions.assertEquals(-210.0, calculate("(900-250+140)-(400+900/3-200)-500"));
    }

}