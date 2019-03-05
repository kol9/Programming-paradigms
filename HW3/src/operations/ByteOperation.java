package operations;

import exceptions.EvaluateException;
import exceptions.OverflowException;
import exceptions.ParsingException;

import java.nio.ByteBuffer;

/**
 * @author Nikolay Yarlychenko
 */
public class ByteOperation implements Operation<Byte> {
    private void checkDiv(Byte x, Byte y) throws EvaluateException {
        if (y == 0) {
            throw new EvaluateException("Divide by zero");
        }
    }

    @Override
    public Byte add(Byte x, Byte y) throws OverflowException {
        return (byte) (x + y);
    }

    @Override
    public Byte sub(Byte x, Byte y) throws OverflowException {
        return (byte) (x - y);
    }

    @Override
    public Byte mul(Byte x, Byte y) throws OverflowException {
        return (byte) (x * y);
    }

    @Override
    public Byte div(Byte x, Byte y) throws EvaluateException {
        checkDiv(x, y);
        return (byte) (x / y);
    }


    @Override
    public Byte neg(Byte x) throws OverflowException {
        return (byte) (-x);
    }

    @Override
    public Byte abs(Byte x) throws OverflowException {
        return (byte) (Math.abs(x));
    }

    @Override
    public Byte square(Byte x) throws OverflowException {
        return (byte) (x * x);
    }

    @Override
    public Byte mod(Byte x, Byte y) throws EvaluateException {
        checkDiv(x, y);
        return (byte) (x % y);
    }

    @Override
    public Byte toCurrentMode(String s) throws EvaluateException {
        try {
            return Byte.parseByte(s);
        } catch (NumberFormatException ignored) {

        }
        return (byte) Integer.parseInt(s);
    }
}
