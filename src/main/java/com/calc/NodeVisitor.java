package com.calc;

public interface NodeVisitor {
    Result accept(BinaryOperatorNode node, Variables variables, Functions functions);
    Result accept(DefineNode node, Variables variables, Functions functions);
    Result accept(FunctionCallNode node, Variables variables, Functions functions);
    Result accept(NumberNode node, Variables variables, Functions functions);
    Result accept(VariableNode node, Variables variables, Functions functions);
}
