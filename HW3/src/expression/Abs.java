package expression;

import exceptions.EvaluateException;
import operations.Operation;

/**
 * @author Nikolay Yarlychenko
 */
public class Abs<T> extends AbstractUnaryOperation<T> {

    public Abs(TripleExpression<T> operand, Operation<T> operation) {
        super(operand, operation);
    }

    @Override
    T apply(T x) throws EvaluateException {
        return operation.abs(x);
    }
}
