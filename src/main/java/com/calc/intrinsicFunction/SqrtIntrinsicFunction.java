package com.calc.intrinsicFunction;

import com.calc.Context;
import com.calc.command.EvalStrictNodeVisitor;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.command.ResultType;
import com.calc.lexer.Type;
import com.calc.node.*;

public class SqrtIntrinsicFunction implements IntrinsicFunction {
    private final Node argument;
    public SqrtIntrinsicFunction(Node argument){
        this.argument = argument;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result res = argument.accept(visitor, context);
        if (res.getType() == ResultType.VAL) {
            double val = res.getVal();
            return new Result(Math.sqrt(val));
        }
        return new Result(new IntrinsicFunctionNode(new SqrtIntrinsicFunction(res.getExpression())));
    }

    @Override
    public Result getDerivative() {
        Node denominator = new BinaryOperatorNode(Type.MUL, new NumberNode(2), new IntrinsicFunctionNode(this));
        return new Result(new BinaryOperatorNode(Type.DIV, new NumberNode(1), denominator));
    }
}
