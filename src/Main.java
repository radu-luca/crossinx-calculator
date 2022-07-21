import calculator.Calculator;
import exceptions.InvalidOperatorException;
import operations.Addition;
import operations.Division;
import operations.Multiplication;
import operations.Subtraction;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String expression = "(3+2)*8/4";
        var operators = new ArrayList<>(
                Arrays.asList(
                        new Addition(),
                        new Division(),
                        new Multiplication(),
                        new Subtraction()
                )
        );
        try {
            var calculator = new Calculator(operators);
            System.out.println(
                    calculator.executeAlgorithm(expression)
            );
        } catch (InvalidOperatorException e) {
            System.out.println("Invalid operator");
        }

    }
}