package operations;


import exceptions.OverflowException;

import java.math.BigInteger;

/**
 * @author Nikolay Yarlychenko
 */
public class BigIntegerOperation implements Operation<BigInteger> {

    private void checkDiv(BigInteger x, BigInteger y) throws OverflowException {
        if(y.equals(BigInteger.ZERO)) {
            throw new OverflowException();
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
    public BigInteger div(BigInteger x, BigInteger y) throws OverflowException {
        checkDiv(x, y);
        return x.divide(y);
    }

    @Override
    public BigInteger neg(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger parseNumber(String s) {
        try {
            return new BigInteger(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
