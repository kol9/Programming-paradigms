package expression;

import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    void check(int x, int y) throws OverflowException {
        if ((x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) ||
                (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y)) {
            throw new OverflowException();
        }
    }

    public int operation(int first, int second) throws OverflowException {
        check(first, second);
        return first - second;
    }
}
