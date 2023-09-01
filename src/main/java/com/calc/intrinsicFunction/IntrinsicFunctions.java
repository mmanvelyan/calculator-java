package com.calc.intrinsicFunction;

import com.calc.command.*;
import com.calc.node.FunctionCallNode;
import com.calc.node.IntrinsicFunctionNode;
import com.calc.node.Node;

import java.util.*;

public class IntrinsicFunctions {

    private static final List<String> intrinsicFunctions = Arrays.asList("sqrt", "exp", "ln", "lg", "sin", "cos", "tan", "arcsin",
            "arccos", "arctan", "abs", "log", "mod");

    private static final Map<String, Integer> argumentsCount = new HashMap<String, Integer>() {{
        put("sqrt", 1);
        put("exp", 1);
        put("ln", 1);
        put("lg", 1);
        put("sin", 1);
        put("cos", 1);
        put("tan", 1);
        put("arcsin", 1);
        put("arccos", 1);
        put("arctan", 1);
        put("abs", 1);
        put("log", 2);
        put("mod", 2);
    }};

    public static boolean isIntrinsic(String name) {
        return intrinsicFunctions.contains(name);
    }

    public static int getArgumentsCount(String name){
        return argumentsCount.get(name);
    }

    private static IntrinsicFunctionNode createIntrinsicFunctionNode(String name, List<Node> arguments) {
        if (!isIntrinsic(name) || (arguments.size() != getArgumentsCount(name))){
            throw new RuntimeException("not intrinsic");
        }
        switch (name) {
            case "sqrt":
                return new IntrinsicFunctionNode(new SqrtIntrinsicFunction(arguments.get(0)));
            case "exp":
                return new IntrinsicFunctionNode(new ExpIntrinsicFunction(arguments.get(0)));
            case "ln":
                return new IntrinsicFunctionNode(new LnIntrinsicFunction(arguments.get(0)));
            case "lg":
                return new IntrinsicFunctionNode(new LgIntrinsicFunction(arguments.get(0)));
            case "sin":
                return new IntrinsicFunctionNode(new SinIntrinsicFunction(arguments.get(0)));
            case "cos":
                return new IntrinsicFunctionNode(new CosIntrinsicFunction(arguments.get(0)));
            case "tan":
                return new IntrinsicFunctionNode(new TanIntrinsicFunction(arguments.get(0)));
            case "arcsin":
                return new IntrinsicFunctionNode(new ArcsinIntrinsicFunction(arguments.get(0)));
            case "arccos":
                return new IntrinsicFunctionNode(new ArccosIntrinsicFunction(arguments.get(0)));
            case "arctan":
                return new IntrinsicFunctionNode(new ArctanIntrinsicFunction(arguments.get(0)));
            case "abs":
                return new IntrinsicFunctionNode(new AbsIntrinsicFunction(arguments.get(0)));
            case "log":
                return new IntrinsicFunctionNode(new LogIntrinsicFunction(arguments.get(0), arguments.get(1)));
            case "mod":
                return new IntrinsicFunctionNode(new ModIntrinsicFunction(arguments.get(0), arguments.get(1)));
            default:
                throw new RuntimeException("not intrinsic");
        }
    }
}