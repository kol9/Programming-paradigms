package expression;

import expression.exceptions.EvaluateException;
import expression.exceptions.OverflowException;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018.
 */
public abstract class UnaryOperation implements TripleExpression {
    private TripleExpression operand;

    UnaryOperation(TripleExpression x) {
        operand = x;
    }

    abstract int operation(final int x) throws EvaluateException;
    abstract void check(int x) throws EvaluateException;

    public int evaluate(int x, int y, int z) throws EvaluateException {
        return operation(operand.evaluate(x, y, z));
    }

}
