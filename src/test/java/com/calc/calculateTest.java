package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class calculateTest {

    Calculator calc = new Calculator();
    @Test
    public void add() {
        Assertions.assertEquals(5.0, calc.calculate("2+3").getRes());
    }
    @Test
    public void mult() {
        Assertions.assertEquals(6.0, calc.calculate("2*3").getRes());
    }
    @Test
    public void sub() {
        Assertions.assertEquals(-1.0, calc.calculate("2-3").getRes());
    }

    @Test
    public void leadingMinus() {
        Assertions.assertEquals(1.0, calc.calculate("-2+3").getRes());
    }


    @Test
    public void div() {
        Assertions.assertEquals(1.5, calc.calculate("3/2").getRes());
    }

    @Test
    public void divBy0() {
        Assertions.assertThrows(ArithmeticException.class, () -> calc.calculate("1/0"));
    }

    @Test
    public void missingClosingBracket() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("(2+3"));
    }

    @Test
    public void numberBeforeBrackets() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("2(3)"));
    }

    @Test
    public void numberAfterBrackets() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("(3)2"));
    }

    @Test
    public void missingOpenBracket() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("(2+3))"));
    }


    @Test
    public void brackets() {
        Assertions.assertEquals(5.0, calc.calculate("(2+3)").getRes());
    }

    @Test
    public void emptyBrackets() {
        Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("()"));
    }

    @Test
    public void correctPosition() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("(2 +3 ) 4"));
        Assertions.assertEquals(8, thrown.getPos());
    }

    @Test
    public void correctPositionBrackets() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("2 + (4 - + 5)"));
        Assertions.assertEquals(9, thrown.getPos());
    }
    @Test
    public void correctPositionVariableName() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("x 2 = 5"));
        Assertions.assertEquals(0, thrown.getPos());
    }
    @Test
    public void correctPositionVariableNameDigitFirst() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("2x = 5"));
        Assertions.assertEquals(1, thrown.getPos());
    }
    @Test
    public void correctPositionVariableValue() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calc.calculate("x = 3+*4"));
        Assertions.assertEquals("pos 6", thrown.getMessage().substring(0, 5));
    }

    @Test
    public void divOrder() {
        Assertions.assertEquals(0.25, calc.calculate("1/2/2").getRes());
    }

    @Test
    public void subOrder() {
        Assertions.assertEquals(0.0, calc.calculate("5-2-3").getRes());
    }

    @Test
    public void multAddOrder() {
        Assertions.assertEquals(6.0, calc.calculate("2+2*2").getRes());
    }

    @Test
    public void multSubOrder() {
        Assertions.assertEquals(-2.0, calc.calculate("2-2*2").getRes());
    }

    @Test
    public void multBracketsOrder() {
        Assertions.assertEquals(8.0, calc.calculate("(2+2)*2").getRes());
    }

    @Test
    public void calculateTestMixed() {
        Assertions.assertEquals(36.0, calc.calculate("( 2+ 7) * 3 + 9").getRes());
        Assertions.assertEquals(25.0, calc.calculate("42/6+(70-64)*5+52/26-70/5").getRes());
        Assertions.assertEquals(93.0, calc.calculate("70-(81-39)/7+6*7-90/5+85/17").getRes());
        Assertions.assertEquals(-210.0, calc.calculate("(900-250+140)-(400+900/3-200)-500").getRes());
    }



}