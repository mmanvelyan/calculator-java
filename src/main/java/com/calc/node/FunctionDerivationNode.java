package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;

import java.util.List;

public class FunctionDerivationNode implements Node {

    private final FunctionCallNode function;
    private final int derivationDegree;

    public FunctionDerivationNode(FunctionCallNode function, int derivationDegree) {
        this.function = function;
        this.derivationDegree = derivationDegree;
    }

    public FunctionCallNode getFunction() {
        return function;
    }

    public int getDerivationDegree() {
        return derivationDegree;
    }

    @Override
    public Result accept(NodeVisitor visitor, Context context) {
        return visitor.accept(this, context);
    }
}
