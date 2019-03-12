package operations;

import exceptions.EvaluateException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;


/**
 * @author Nikolay Yarlychenko
 */
public class IntegerOperation implements Operation<Integer> {
    private boolean isChecked;

    public IntegerOperation(boolean isChecked) {
        this.isChecked = isChecked;
    }

    private void checkAdd(int x, int y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    private void checkSub(Integer x, Integer y) throws OverflowException {
        if ((x >= 0 && y < 0 && x - Integer.MAX_VALUE > y)
                || (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y)) {
            throw new OverflowException();
        }
    }

    private void checkMul(int x, int y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    private void checkDiv(int x, int y) throws IllegalOperationException, OverflowException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        if (!isChecked && x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    private void checkNegate(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    @Override
    public Integer add(Integer x, Integer y) throws OverflowException {
        if (!isChecked) {
            checkAdd(x, y);
        }
        return x + y;
    }

    @Override
    public Integer sub(Integer x, Integer y) throws OverflowException {
        if (!isChecked) {
            checkSub(x, y);
        }
        return x - y;
    }

    @Override
    public Integer mul(Integer x, Integer y) throws OverflowException {
        if (!isChecked) {
            checkMul(x, y);
        }
        return x * y;
    }

    @Override
    public Integer div(Integer x, Integer y) throws EvaluateException {
        checkDiv(x, y);
        return x / y;
    }

    @Override
    public Integer neg(Integer x) throws OverflowException {
        if (!isChecked) {
            checkNegate(x);
        }
        return -x;
    }

    @Override
    public Integer abs(Integer x) throws OverflowException {
        if (x > 0) {
            return x;
        } else {
            return neg(x);
        }
    }

    @Override
    public Integer square(Integer x) throws OverflowException {
        return mul(x, x);
    }

    public Integer mod(Integer x, Integer y) throws OverflowException, IllegalOperationException {
        checkDiv(x, y);
        return x % y;
    }


    @Override
    public Integer toCurrentMode(String s) throws EvaluateException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new EvaluateException("Number format error");
        }
    }
}
