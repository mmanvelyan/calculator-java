package com.calc;

public interface Node {
    Result eval(Variables variables, Functions functions);
    String rpn(Variables variables, Functions functions);
}
