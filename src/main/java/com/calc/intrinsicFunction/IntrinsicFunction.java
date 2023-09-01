package com.calc.intrinsicFunction;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;

public interface IntrinsicFunction {
    Result evaluate(NodeVisitor visitor, Context context);
    Result getDerivative();
}
