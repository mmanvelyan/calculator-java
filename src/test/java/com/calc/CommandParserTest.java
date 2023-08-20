package com.calc;

import com.calc.command.EvalNodeVisitor;
import com.calc.command.EvalStrictNodeVisitor;
import com.calc.command.ReversePolishNotationNodeVisitor;
import com.calc.parser.CommandParser;
import com.calc.parser.UnexpectedCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser();

    @Test
    public void evalStrictCommand(){
        Assertions.assertEquals(EvalStrictNodeVisitor.class, commandParser.parse("eval strict").getClass());
    }

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