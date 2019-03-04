package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by Nikolay Yarlychenko on 03/12/2018
 */
public interface TripleExpression {
    int evaluate(int x, int y, int z) throws EvaluateException;
}
