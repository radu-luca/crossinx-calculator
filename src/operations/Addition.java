package operations;


import operations.interfaces.IOperation;

public class Addition implements IOperation {
    private final String sign = "+";
    @Override
    public double calculate(double firstNumber, double secondNumber) {
        return firstNumber + secondNumber;
    }

    @Override
    public String getSign() {
        return sign;
    }
}
