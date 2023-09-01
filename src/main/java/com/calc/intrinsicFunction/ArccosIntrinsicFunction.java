package com.calc.intrinsicFunction;

import com.calc.Context;
import com.calc.command.EvalStrictNodeVisitor;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.command.ResultType;
import com.calc.lexer.Type;
import com.calc.node.*;

public class ArccosIntrinsicFunction implements IntrinsicFunction {

    private final Node argument;

    public ArccosIntrinsicFunction(Node argument) {
        this.argument = argument;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result res = argument.accept(visitor, context);
        if (res.getType() == ResultType.VAL) {
            double val = res.getVal();
            return new Result(Math.acos(val));
        }
        return new Result(new IntrinsicFunctionNode(new ArccosIntrinsicFunction(res.getExpression())));
    }

    @Override
    public Result getDerivative() {
        Node x2 = new BinaryOperatorNode(Type.POWER, argument, new NumberNode(2));
        Node radExpr = new BinaryOperatorNode(Type.SUB, new NumberNode(1), x2);
        Node denominator = new IntrinsicFunctionNode(new SqrtIntrinsicFunction(radExpr));
        Node minusResult = new BinaryOperatorNode(Type.DIV, new NumberNode(1), denominator);
        return new Result(new UnaryOperatorNode(Type.SUB, minusResult));
    }
}
