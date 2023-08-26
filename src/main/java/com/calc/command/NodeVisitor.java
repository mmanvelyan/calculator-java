package com.calc.command;

import com.calc.Context;
import com.calc.node.*;

public interface NodeVisitor {
    Result accept(BinaryOperatorNode node, Context context);
    Result accept(UnaryOperatorNode node, Context context);
    Result accept(DefineNode node, Context context);
    Result accept(FunctionCallNode node, Context context);
    Result accept(NumberNode node, Context context);
    Result accept(VariableNode node, Context context);
}
