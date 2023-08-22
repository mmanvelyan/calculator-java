package com.calc.node;

import com.calc.Context;
import com.calc.command.NodeVisitor;
import com.calc.command.Result;
import com.calc.lexer.Type;

public class BinaryOperatorNode implements Node {
    private final Type operator;
    private final Node l;
    private final Node r;

    public BinaryOperatorNode(Type operator, Node l, Node r){
        this.operator = operator;
        this.l = l;
        this.r = r;
    }

    public Type getOperator() {
        return operator;
    }

    public Node getL() {
        return l;
    }

    public Node getR() {
        return r;
    }

    public Result accept(NodeVisitor visitor, Context context){
        return visitor.accept(this, context);
    }

    public String toString(){
        return "[operator = " + operator.toString() + ", l = " + l.toString() + ", r = " + r.toString() + "]";
    }

}
