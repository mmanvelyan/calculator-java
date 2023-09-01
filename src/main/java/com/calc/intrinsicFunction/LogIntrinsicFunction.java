package com.calc.intrinsicFunction;

import com.calc.Context;
import com.calc.command.*;
import com.calc.lexer.Type;
import com.calc.node.BinaryOperatorNode;
import com.calc.node.IntrinsicFunctionNode;
import com.calc.node.Node;
import com.calc.node.NumberNode;

public class LogIntrinsicFunction implements IntrinsicFunction {

    private final Node base;
    private final Node argument;

    public LogIntrinsicFunction(Node base, Node argument) {
        this.base = base;
        this.argument = argument;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result resBase = base.accept(visitor, context);
        Result resArg = argument.accept(visitor, context);
        if (resBase.getType() == ResultType.VAL && resArg.getType() == ResultType.VAL) {
            double valBase = resBase.getVal();
            double valArg = resArg.getVal();
            return new Result(Math.log(valArg)/Math.log(valBase));
        }
        Node exprBase = resBase.getExpression();
        Node exprArg = resArg.getExpression();
        return new Result(new IntrinsicFunctionNode(new LogIntrinsicFunction(exprBase, exprArg)));
    }

    @Override
    public Result getDerivative() {
        Node lnNode = new IntrinsicFunctionNode(new LnIntrinsicFunction(base));
        Node denominator = new BinaryOperatorNode(Type.MUL, lnNode, argument);
        return new Result(new BinaryOperatorNode(Type.DIV, new NumberNode(1), denominator));
    }
}
