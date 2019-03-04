package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 11/12/2018
 */
public class Log extends UnaryOperation{
    public Log(TripleExpression x) {
        super(x);
    }

    void check(int x) throws EvaluateException, OverflowException {
        if(x == 1)throw new EvaluateException("argument of log = 1");
    }

    int operation(int x) throws EvaluateException {
        check(x);
        int l = 0;
        int r = 31;

        while(r - l > 1) {
            int m = (l + r) / 2;
            if((1 << m) < x) {
                l = m;
            } else r = m;
        }
        return l;
    }
}
