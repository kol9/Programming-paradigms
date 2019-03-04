package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.IllegalOperationException;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class Sqrt extends UnaryOperation {

    public Sqrt(TripleExpression x) {
        super(x);
    }

    void check(int x) throws EvaluateException {
        if (x < 0) {
            throw new IllegalOperationException("Sqrt from negative number");
        }
    }

    int operation(int x) throws EvaluateException {
        check(x);
        int l = 0;
        int r = 46340;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (m * m <= x) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

}
