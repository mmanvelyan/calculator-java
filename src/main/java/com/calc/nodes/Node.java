package com.calc.nodes;

import com.calc.Functions;
import com.calc.commands.NodeVisitor;
import com.calc.commands.Result;
import com.calc.Variables;

public interface Node {
    Result accept(NodeVisitor visitor, Variables variables, Functions functions);
}
