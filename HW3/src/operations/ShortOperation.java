package operations;

import exceptions.EvaluateException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

/**
 * @author Nikolay Yarlychenko
 */
public class ShortOperation implements Operation<Short> {
    @Override
    public Short add(Short x, Short y) throws OverflowException {
        short res = x;
        res += y;
        return res;
    }

    @Override
    public Short sub(Short x, Short y) throws OverflowException {
        short res = x;
        res += y;
        return res;
    }

    @Override
    public Short mul(Short x, Short y) throws OverflowException {
        short res = x;
        res += y;
        return res;
    }

    @Override
    public Short div(Short x, Short y) throws EvaluateException {
        checkDiv(x, y);
        short res = x;
        res += y;
        return res;
    }

    private void checkDiv(Short x, Short y) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
    }

    @Override
    public Short neg(Short x) throws OverflowException {
        short res = x;
        res *= -1;
        return res;
    }

    @Override
    public Short abs(Short x) throws OverflowException {
        if (x > 0) return x;
        else return neg(x);
    }
    public Short square(Short x) throws OverflowException {
        return 0;
    }

    @Override
    public Short mod(Short x, Short y) throws OverflowException {
        return null;
    }

    public Short  mod(Integer x, Integer y) throws OverflowException {
        return 0;
    }

    @Override
    public Short toCurrentMode(String s) throws EvaluateException {
        try {
            return Short.parseShort(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
