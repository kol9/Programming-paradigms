package expression;

import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class Abs extends UnaryOperation {

    public Abs(TripleExpression x) {
        super(x);
    }

    void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
    int operation(int x) throws OverflowException{
        check(x);
        if(x < 0) {
            return -x;
        }
        return x;
    }

}
