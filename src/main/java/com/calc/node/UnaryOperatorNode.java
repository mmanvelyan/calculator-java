package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.lexer.Type;

public class UnaryOperatorNode implements Node {
    private final Type operator;
    private final Node operand;

    public UnaryOperatorNode(Type operator, Node operand){
        this.operator = operator;
        this.operand = operand;
    }

    public Type getOperator() {
        return operator;
    }

    public Node getOperand() {
        return operand;
    }

    public Result accept(NodeVisitor visitor, Context context){
        return visitor.accept(this, context);
    }

    public String toString(){
        return "[operator = " + operator.toString() + ", expression = " + operand.toString() + "]";
    }

}
