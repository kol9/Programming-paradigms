package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class CheckedAdd extends BinaryOperation {
    public CheckedAdd(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    void check(int x, int y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    public int operation(int first, int second) throws EvaluateException {
        check(first, second);
        return first + second;
    }
}
