package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class calculateTest {

    MathExpressionParser calc = new MathExpressionParser();
    Variables variables = new Variables();
    
    private float calculate(String s){
        Node tree = calc.parse(s);
        Eval e = new Eval();
        Result res = e.eval(tree, variables);
        return res.getVal();
    }
    
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
    public void leadingMinus() {
        Assertions.assertEquals(1.0, calculate("-2+3"));
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
    public void ass() {
        Assertions.assertEquals(5, calculate("x=5"));
    }

    @Test
    public void missingClosingBracket() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("(2+3"));
        Assertions.assertEquals(4, thrown.getPos());
        Assertions.assertEquals(Type.END, thrown.getToken().getType());
    }

    @Test
    public void numberBeforeBrackets() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("2(3)"));
        Assertions.assertEquals(1, thrown.getPos());
        Assertions.assertEquals(Type.OPEN_BR, thrown.getToken().getType());
    }

    @Test
    public void numberAfterBrackets() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("(3)2"));
        Assertions.assertEquals(3, thrown.getPos());
        Assertions.assertEquals(Type.NUM, thrown.getToken().getType());
    }

    @Test
    public void missingOpenBracket() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("(2+3))"));
        Assertions.assertEquals(5, thrown.getPos());
        Assertions.assertEquals(Type.CLOSING_BR, thrown.getToken().getType());
    }


    @Test
    public void brackets() {
        Assertions.assertEquals(5.0, calculate("(2+3)"));
    }

    @Test
    public void emptyBrackets() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("()"));
        Assertions.assertEquals(1, thrown.getPos());
        Assertions.assertEquals(Type.CLOSING_BR, thrown.getToken().getType());
    }

    @Test
    public void correctPosition() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("(2 +3 ) 4"));
        Assertions.assertEquals(8, thrown.getPos());
    }

    @Test
    public void twoOperatorsInSequence() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("2 + (4 - + 5)"));
        Assertions.assertEquals(9, thrown.getPos());
        Assertions.assertEquals(Type.ADD, thrown.getToken().getType());
    }

    @Test
    public void wrongVariableName() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("x 2 = 5"));
        Assertions.assertEquals(2, thrown.getPos());
        Assertions.assertEquals(Type.NUM, thrown.getToken().getType());
    }

    @Test
    public void wrongVariableNameDigitFirst() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("2x = 5"));
        Assertions.assertEquals(1, thrown.getPos());
        Assertions.assertEquals(Type.VAR, thrown.getToken().getType());
    }

    @Test
    public void incorrectAssNoVariable() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("2 + 3 = 4 + 1"));
        Assertions.assertEquals(6, thrown.getPos());
        Assertions.assertEquals(Type.ASS, thrown.getToken().getType());
    }

    @Test
    public void incorrectAssTermLeft() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("2 + x = 5"));
        Assertions.assertEquals(6, thrown.getPos());
        Assertions.assertEquals(Type.ASS, thrown.getToken().getType());
    }

    @Test
    public void correctPositionVariableValue() {
        UnexpectedTokenException thrown = Assertions.assertThrows(UnexpectedTokenException.class, () -> calculate("x = 3+*4"));
        Assertions.assertEquals(6, thrown.getPos());
        Assertions.assertEquals(Type.MUL, thrown.getToken().getType());
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






