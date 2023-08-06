package com.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser();

    @Test
    public void evalCommand(){
        Assertions.assertEquals(Eval.class, commandParser.parse("eval").getClass());
    }

    @Test
    public void rpnCommand(){
        Assertions.assertEquals(ReversePolishNotation.class, commandParser.parse("rpn").getClass());
    }

    @Test
    public void unexpectedCommand(){
        Assertions.assertThrows(UnexpectedCommandException.class, ()->commandParser.parse("prn"));
    }

}