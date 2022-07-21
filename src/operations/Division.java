package operations;


import operations.interfaces.IOperation;

public class Division implements IOperation {
    private final String sign = "/";
    @Override
    public double calculate(double firstNumber, double secondNumber) {
        return secondNumber / firstNumber;
    }

    @Override
    public String getSign() {
        return sign;
    }
}
