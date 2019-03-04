package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by Nikolay Yarlychenko on 11/02/2019
 */
public class High extends UnaryOperation {

    public High(TripleExpression x) {
        super(x);
    }

    int operation(int x) throws EvaluateException {
        return Integer.highestOneBit(x);
    }

    void check(int x) throws EvaluateException {
    }
}
