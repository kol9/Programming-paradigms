package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * @author Nikolay Yarlychenko
 */
public abstract class AbstractBinaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> first;
    private TripleExpression<T> second;
    protected Operation<T> operation;

    AbstractBinaryOperation(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }


    abstract T apply(T x, T y) throws EvaluateException;

    @Override
    public T evaluate(T x, T y, T z) throws EvaluateException {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
