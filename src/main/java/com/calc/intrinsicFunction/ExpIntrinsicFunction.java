package com.calc.intrinsicFunction;

import com.calc.Context;
import com.calc.command.EvalStrictNodeVisitor;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.command.ResultType;
import com.calc.lexer.Type;
import com.calc.node.BinaryOperatorNode;
import com.calc.node.IntrinsicFunctionNode;
import com.calc.node.Node;
import com.calc.node.NumberNode;

public class ExpIntrinsicFunction implements IntrinsicFunction {

    private final Node argument;

    public ExpIntrinsicFunction(Node argument) {
        this.argument = argument;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result res = argument.accept(visitor, context);
        if (res.getType() == ResultType.VAL) {
            double val = res.getVal();
            return new Result(Math.exp(val));
        }
        return new Result(new IntrinsicFunctionNode(new ExpIntrinsicFunction(res.getExpression())));
    }

    @Override
    public Result getDerivative() {
        return new Result(new IntrinsicFunctionNode(this));
    }
}
