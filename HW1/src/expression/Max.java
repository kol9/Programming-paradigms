package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class Max extends BinaryOperation{
    public Max(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    void check(int x, int y) throws EvaluateException {
    }

    int operation(int first, int second) throws EvaluateException {
        if(first > second) {
            return first;
        } else {
            return second;
        }
    }
}
