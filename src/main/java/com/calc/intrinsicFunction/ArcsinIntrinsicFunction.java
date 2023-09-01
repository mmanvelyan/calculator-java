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

public class ArcsinIntrinsicFunction implements IntrinsicFunction {

    private final Node argument;

    public ArcsinIntrinsicFunction(Node argument) {
        this.argument = argument;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result res = argument.accept(visitor, context);
        if (res.getType() == ResultType.VAL) {
            double val = res.getVal();
            return new Result(Math.asin(val));
        }
        return new Result(new IntrinsicFunctionNode(new ArcsinIntrinsicFunction(res.getExpression())));
    }

    @Override
    public Result getDerivative() {
        Node x2 = new BinaryOperatorNode(Type.POWER, argument, new NumberNode(2));
        Node radExpr = new BinaryOperatorNode(Type.SUB, new NumberNode(1), x2);
        Node denominator = new IntrinsicFunctionNode(new SqrtIntrinsicFunction(radExpr));
        return new Result(new BinaryOperatorNode(Type.DIV, new NumberNode(1), denominator));
    }
}
