package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * @author Nikolay Yarlychenko
 */
public abstract class AbstractUnaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> operand;
    protected Operation<T> operation;

    AbstractUnaryOperation(TripleExpression<T> operand, Operation<T> operation) {
        this.operand = operand;
        this.operation = operation;
    }

    abstract T apply(T x) throws EvaluateException;

    @Override
    public T evaluate(T x, T y, T z) throws EvaluateException {
        return apply(operand.evaluate(x, y, z));
    }
}
