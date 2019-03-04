package expression;

import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(TripleExpression first, TripleExpression second) {
        super(first, second);
    }


    void check(int x, int y) throws OverflowException {
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

    public int operation(int first, int second) throws OverflowException {
        check(first, second);
        return first * second;
    }
}
