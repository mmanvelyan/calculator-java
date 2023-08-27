package com.calc;

import com.calc.command.EvalNodeVisitor;
import com.calc.command.PrintNodeVisitor;
import com.calc.command.Result;
import com.calc.command.ResultType;
import com.calc.node.Node;
import com.calc.parser.MathExpressionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class DerivationTest {

    private final Context context = new Context();

    private String calculate(String s){
        MathExpressionParser calc = new MathExpressionParser();
        Node tree = calc.parse(s);
        EvalNodeVisitor eval = new EvalNodeVisitor();
        Result resultExpression = tree.accept(eval, context);
        if (resultExpression.getType() == ResultType.VAL){
            return String.valueOf(resultExpression.getVal());
        }
        Result result = resultExpression.getExpression().accept(new PrintNodeVisitor(), context);
        return result.getStr();
    }

    @Test
    public void constantMulVariableDerivation(){
        Assertions.assertEquals("2.0", calculate("(2*x)'"));
    }

    @Test
    public void variableDerivation(){
        Assertions.assertEquals("1.0", calculate("x'"));
    }

    @Test
    public void knownVariableDerivation(){
        Assertions.assertEquals("2.0", calculate("x = 2"));
        Assertions.assertEquals("0.0", calculate("x'"));
    }

    @Test
    public void multipleVariableDerivation(){
        Assertions.assertEquals("3.0", calculate("(2*x+y)'"));
    }

    @Test
    public void constantDerivation(){
        Assertions.assertEquals("0.0", calculate("5'"));
    }

    @Test
    public void addDerivation(){
        Assertions.assertEquals("2*x+1", calculate("(x^2+x)'"));
    }

    @Test
    public void subDerivation(){
        Assertions.assertEquals("2*x-1", calculate("(x^2-x)'"));
    }

    @Test
    public void mulDerivation(){
        Assertions.assertEquals("2*x*x+x^2*1", calculate("(x^2*x)'"));
    }

    @Test
    public void divDerivation(){
        Assertions.assertEquals("(2*x*x-x^2*1)/x^2", calculate("(x^2/x)'"));
    }

    @Test
    public void powerDerivation(){
        Assertions.assertEquals("4*x^3", calculate("(x^4)'"));
    }

    @Test
    public void functionDerivation(){
        Assertions.assertEquals("f'(x)", calculate("f'(x)"));
    }

    @Test
    public void functionAddDerivation(){
        Assertions.assertEquals("f'(x)+g'(x)", calculate("(f(x)+g(x))'"));
    }

    @Test
    public void functionSubDerivation(){
        Assertions.assertEquals("f'(x)-g'(x)", calculate("(f(x)-g(x))'"));
    }

    @Test
    public void functionMulDerivation(){
        Assertions.assertEquals("f'(x)*g(x)+f(x)*g'(x)", calculate("(f(x)*g(x))'"));
    }

    @Test
    public void functionDivDerivation(){
        Assertions.assertEquals("(f'(x)*g(x)-f(x)*g'(x))/g(x)^2", calculate("(f(x)*g(x))'"));
    }

    @Test
    public void functionPowDerivation(){
        Assertions.assertEquals("3*f'(x)*f(x)^2", calculate("(f(x)^3)'"));
    }

    @Test
    public void functionCompositionDerivation(){
        Assertions.assertEquals("g'(x)*f'(g(x))", calculate("f'(g(x))"));
    }

    @Test
    public void pointDerivation(){
        Assertions.assertEquals("x^2", calculate("f(x) = x^2"));
        Assertions.assertEquals("10.0", calculate("f'(5)"));
    }


}
