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

public class TanIntrinsicFunction implements IntrinsicFunction {

    private final Node argument;

    public TanIntrinsicFunction(Node argument) {
        this.argument = argument;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result res = argument.accept(visitor, context);
        if (res.getType() == ResultType.VAL) {
            double val = res.getVal();
            return new Result(Math.tan(val));
        }
        return new Result(new IntrinsicFunctionNode(new TanIntrinsicFunction(res.getExpression())));
    }

    @Override
    public Result getDerivative() {
        Node cosNode = new IntrinsicFunctionNode(new CosIntrinsicFunction(argument));
        Node denominator = new BinaryOperatorNode(Type.POWER, cosNode, new NumberNode(2));
        return new Result(new BinaryOperatorNode(Type.DIV, new NumberNode(1), denominator));
    }
}
