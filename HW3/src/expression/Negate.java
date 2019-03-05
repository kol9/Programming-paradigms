package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public class Negate<T> extends AbstractUnaryOperation<T> {
    public Negate(TripleExpression<T> first, Operation<T> operation) {
        super(first, operation);
    }

    @Override
    T apply(T x) throws EvaluateException {
        return operation.neg(x);
    }
}
