package com.calc;

public interface Node {
    Result accept(NodeVisitor visitor, Variables variables, Functions functions);
}
