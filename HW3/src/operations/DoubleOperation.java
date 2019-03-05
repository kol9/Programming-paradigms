package operations;


import exceptions.EvaluateException;
import exceptions.OverflowException;

/**
 * @author Nikolay Yarlychenko
 */
public class DoubleOperation implements Operation<Double> {

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double neg(Double x) {
        return -x;
    }

    @Override
    public Double abs(Double x) throws OverflowException {

        return Math.abs(x);
    }

    public Double square(Double x) throws OverflowException {
        return x * x;
    }

    public Double mod(Double x, Double y) throws OverflowException {
        return x % y;
    }

    @Override
    public Double toCurrentMode(String s) throws EvaluateException {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new EvaluateException("Number format error");

        }
    }
}
