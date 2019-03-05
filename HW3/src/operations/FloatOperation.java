package operations;


import exceptions.EvaluateException;
import exceptions.OverflowException;

/**
 * @author Nikolay Yarlychenko
 */
public class FloatOperation implements Operation<Float> {
    @Override
    public Float add(Float x, Float y) throws OverflowException {
        return x + y;
    }

    @Override
    public Float sub(Float x, Float y) throws OverflowException {
        return x - y;
    }

    @Override
    public Float mul(Float x, Float y) throws OverflowException {
        return x * y;
    }

    @Override
    public Float div(Float x, Float y) throws EvaluateException {
        return x / y;
    }

    @Override
    public Float neg(Float x) throws OverflowException {
        return -x;
    }

    @Override
    public Float abs(Float x) throws OverflowException {
        return Math.abs(x);
    }

    @Override
    public Float square(Float x) throws OverflowException {
        return x * x;
    }

    @Override
    public Float mod(Float x, Float y) throws EvaluateException {
        return x % y;
    }

    @Override
    public Float toCurrentMode(String s) throws EvaluateException {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
