package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by Nikolay Yarlychenko on 11/02/2019
 */
public class Low extends UnaryOperation {

    public Low(TripleExpression x) {
        super(x);
    }

    int operation(int x) {
        return Integer.lowestOneBit(x);
    }

    void check(int x) {

    }
}
