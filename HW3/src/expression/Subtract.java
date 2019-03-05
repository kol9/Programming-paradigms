package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Subtract<T> extends AbstractBinaryOperation<T> {
    public Subtract(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    @Override
    T apply(T x, T y) throws EvaluateException {
        return operation.sub(x, y);
    }
}
