package com.calc;

import com.calc.commands.EvalNodeVisitor;
import com.calc.commands.ReversePolishNotationNodeVisitor;
import com.calc.parser.CommandParser;
import com.calc.parser.UnexpectedCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser();

    @Test
    public void evalCommand(){
        Assertions.assertEquals(EvalNodeVisitor.class, commandParser.parse("eval").getClass());
    }

    @Test
    public void rpnCommand(){
        Assertions.assertEquals(ReversePolishNotationNodeVisitor.class, commandParser.parse("rpn").getClass());
    }

    @Test
    public void unexpectedCommand(){
        Assertions.assertThrows(UnexpectedCommandException.class, ()->commandParser.parse("prn"));
    }

}