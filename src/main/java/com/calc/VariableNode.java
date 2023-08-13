package com.calc;

public class VariableNode implements Node {
    private final String name;
    private final int pos;

    public VariableNode(String name, int pos){
        this.name = name;
        this.pos = pos;
    }

    public Result eval(Variables variables, Functions functions){
        if (variables.getValue(name) == null) {
            throw new UnexpectedVariableException(name, pos);
        }
        return new Result(variables.getValue(name));
    }

    public String rpn(Variables variables, Functions functions){
        return name+" ";
    }

}
