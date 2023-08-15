package com.calc.nodes;

import com.calc.*;
import com.calc.commands.NodeVisitor;
import com.calc.commands.Result;
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

    public Result accept(NodeVisitor visitor, Variables variables, Functions functions){
        return visitor.accept(this, variables, functions);
    }

}
