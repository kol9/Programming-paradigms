package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Add<T> extends AbstractBinaryOperation<T> {
    public Add(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    @Override
    T apply(T x, T y) throws EvaluateException {
        return operation.add(x, y);
    }
}
