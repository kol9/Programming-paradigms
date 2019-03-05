package operations;


import exceptions.EvaluateException;
import exceptions.OverflowException;

import java.math.BigInteger;

/**
 * @author Nikolay Yarlychenko
 */

public class BigIntegerOperation implements Operation<BigInteger> {

    private void checkDiv(BigInteger x, BigInteger y) throws EvaluateException {
        if (y.equals(BigInteger.ZERO)) {
            throw new EvaluateException("Divide by zero");
        }
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger div(BigInteger x, BigInteger y) throws EvaluateException {
        checkDiv(x, y);
        return x.divide(y);
    }

    @Override
    public BigInteger neg(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger abs(BigInteger x) {
        if (x.compareTo(BigInteger.ZERO) > 0) return x;
        else return neg(x);
    }

    @Override
    public BigInteger square(BigInteger x) {
        return x.multiply(x);
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) throws EvaluateException {
        checkDiv(x, y);
        try {
            return x.mod(y);
        } catch (ArithmeticException e) {
            throw new EvaluateException("Module is not positive");
        }
    }


    @Override
    public BigInteger toCurrentMode(String s) {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
