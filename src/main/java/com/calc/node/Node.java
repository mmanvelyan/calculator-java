package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;

public interface Node {
    Result accept(NodeVisitor visitor, Context context);
}
