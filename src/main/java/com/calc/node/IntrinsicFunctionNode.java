package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.intrinsicFunction.IntrinsicFunction;

public class IntrinsicFunctionNode implements Node {

    private final IntrinsicFunction function;

    public IntrinsicFunctionNode(IntrinsicFunction function) {
        this.function = function;
    }

    public IntrinsicFunction getFunction() {
        return function;
    }
    @Override
    public Result accept(NodeVisitor visitor, Context context) {
        return visitor.accept(this, context);
    }
}
