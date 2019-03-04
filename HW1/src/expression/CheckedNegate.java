package expression;

import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(TripleExpression x) {
        super(x);
    }

    void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    int operation(int x) throws OverflowException {
        check(x);
        return -x;
    }

}
