package expression;

import exceptions.EvaluateException;

/**
 * Created by Nikolay Yarlychenko on 03/12/2018
 */
public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws EvaluateException;
}
