package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Square<T> extends AbstractUnaryOperation<T> {
    public Square(TripleExpression<T> first, Operation<T> operation) {
        super(first, operation);
    }

    @Override
    T apply(T x) throws EvaluateException {
        return operation.square(x);
    }
}
