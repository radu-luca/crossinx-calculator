package UnitTest;

import calculator.Calculator;
import exceptions.InvalidOperatorException;
import operations.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorTest {

    private Calculator calculator;

    public CalculatorTest() {
        var operators = new ArrayList<>(
                Arrays.asList(
                        new Addition(),
                        new Division(),
                        new Multiplication(),
                        new Subtraction()
                )
        );
        this.calculator = new Calculator(operators);
    }

    @Test
    public void ShouldReturnValue10() throws InvalidOperatorException {
        Assertions.assertEquals(calculator.executeAlgorithm("5+5"), 10.0);
    }

    @Test
    public void ShouldReturnValue51() throws InvalidOperatorException {
        String expression = "(10/2)*10+1";
        Assertions.assertEquals(calculator.executeAlgorithm(expression), 51.0);
    }

    @Test
    public void ShouldThrownExceptionInvalidOperator() {
        String expression = "log(10)";
        InvalidOperatorException exception = Assertions.assertThrows(InvalidOperatorException.class, () -> calculator.executeAlgorithm(expression));
        Assertions.assertEquals(exception.getMessage(), "The operator was not defined");
    }

}
