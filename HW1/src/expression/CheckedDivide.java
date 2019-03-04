package expression;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    void check(int x, int y) throws IllegalOperationException, OverflowException {
        if (y == 0) {
            throw new IllegalOperationException("division by zero");
        }
        if(x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    public int operation(int first, int second) throws IllegalOperationException, OverflowException {
        check(first,second);
        return first / second;
    }
}
