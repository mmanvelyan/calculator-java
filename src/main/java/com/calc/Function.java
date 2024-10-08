package com.calc;

import com.calc.command.PrintNodeVisitor;
import com.calc.node.Node;

import java.util.Collections;
import java.util.List;

public class Function {

    private final List<String> args;

    private final Node expression;

    public Function(List<String> args, Node expression) {
        this.args = args;
        this.expression = expression;
    }

    public List<String> getArgs() {
        return Collections.unmodifiableList(args);
    }

    public Node getExpression() {
        return expression;
    }

    public String toString(){
        return "function(" + String.join(", ", args) + ") = " + expression.accept(new PrintNodeVisitor(), new Context());
    }

}
