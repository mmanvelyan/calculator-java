package com.calc.node;

import com.calc.Functions;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.Variables;

public interface Node {
    Result accept(NodeVisitor visitor, Variables variables, Functions functions);
}
