package com.calc.intrinsicFunction;

import com.calc.Context;
import com.calc.command.*;
import com.calc.lexer.Type;
import com.calc.node.BinaryOperatorNode;
import com.calc.node.IntrinsicFunctionNode;
import com.calc.node.Node;
import com.calc.node.NumberNode;

public class ModIntrinsicFunction implements IntrinsicFunction {

    private final Node argument1;
    private final Node argument2;

    public ModIntrinsicFunction(Node argument1, Node argument2) {
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    @Override
    public Result evaluate(NodeVisitor visitor, Context context) {
        Result resArg1 = argument1.accept(visitor, context);
        Result resArg2 = argument2.accept(visitor, context);
        if (resArg1.getType() == ResultType.VAL && resArg2.getType() == ResultType.VAL) {
            double val1 = resArg1.getVal();
            double val2 = resArg2.getVal();
            return new Result(val1%val2);
        }
        Node exprArg1 = resArg1.getExpression();
        Node exprArg2 = resArg2.getExpression();
        return new Result(new IntrinsicFunctionNode(new ModIntrinsicFunction(exprArg1, exprArg2)));
    }

    @Override
    public Result getDerivative() {
        throw new UnexpectedDerivativeException("mod");
    }
}
