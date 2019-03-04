package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 11/12/2018
 */
public class Pow extends UnaryOperation{
    public Pow(TripleExpression x) {
        super(x);
    }

    void check(int x) throws EvaluateException, OverflowException {
        if(x < 0)throw new EvaluateException("argument of pow < 0");
        if(x >= 31)throw new OverflowException();
    }

    int operation(int x) throws EvaluateException {
        check(x);
        return (1 << x);
    }
}
