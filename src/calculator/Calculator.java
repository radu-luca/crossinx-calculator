package calculator;

import exceptions.InvalidOperatorException;
import models.Tuple;
import operations.interfaces.IOperation;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Calculator {
    private final List<IOperation> operations;

    public Calculator(List<IOperation> operations) {
        this.operations = operations;
    }

    public double executeAlgorithm(String expression) throws InvalidOperatorException {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expression.length(); ++i) {
            if(isEmptySpace(expression.charAt(i)))
                continue;
            if (isNumber(expression.charAt(i))) {
                var tuple = extractAllNumbers(i, expression);
                values.push(Double.parseDouble(tuple.value));
                i = tuple.key;
            } else if (expression.charAt(i) == '(') {
                ops.push('(');
            } else if (expression.charAt(i) == ')') {
                performOperationsFromBraces(values, ops);
                ops.pop(); // remove "("
            } else {
                performOperationsWithPrecedence(values, ops, expression.charAt(i));
                ops.push(expression.charAt(i));
            }
        }

        performOperations(values, ops);
        return values.pop();
    }

    private boolean isEmptySpace(char charAt) {
        return charAt == ' ';
    }

    private void performOperations(Stack<Double> values, Stack<Character> operators) throws InvalidOperatorException {
        while (!operators.empty())
        {
            var operator = getOperator(operators.pop());
            values.push(executeOperation(values.pop(), values.pop(), operator));
        }
    }

    private void performOperationsWithPrecedence(Stack<Double> values, Stack<Character> operators, char currentOperator) throws InvalidOperatorException {
        while (!operators.empty() && hasPrecedence(currentOperator, operators.peek())) {
            var operator = getOperator(operators.pop());
            values.push(executeOperation(values.pop(), values.pop(), operator));
        }
    }

    private boolean hasPrecedence(char currentOperator, char lastOperator) {
        if (lastOperator == '(' || lastOperator == ')')
            return false;
        return (currentOperator != '*' && currentOperator != '/') ||
                (lastOperator != '+' && lastOperator != '-');
    }

    private void performOperationsFromBraces(Stack<Double> values, Stack<Character> operators) throws InvalidOperatorException {
        while (operators.peek() != '(') {
            var operator = getOperator(operators.pop());
            values.push(executeOperation(values.pop(), values.pop(), operator));
        }
    }

    private IOperation getOperator(Character operatorChar) throws InvalidOperatorException {
        Optional<IOperation> op = this.operations.stream()
                .filter(o -> o.getSign().charAt(0) == operatorChar)
                .findFirst();
        if (op.isPresent())
            return op.get();
        throw new InvalidOperatorException("The operator was not defined");
    }

    private Tuple<Integer, String> extractAllNumbers(Integer index, String expression) {
        StringBuilder buffer = new StringBuilder();

        while (index < expression.length() &&
                expression.charAt(index) >= '0' && expression.charAt(index) <= '9')
            buffer.append(expression.charAt(index++));

        return new Tuple<>(--index,buffer.toString());
    }

    private double executeOperation(double firstNumber, double secondNumber, IOperation operation) {
        return operation.calculate(firstNumber, secondNumber);
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

}
