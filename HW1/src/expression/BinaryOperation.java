package expression;

import expression.exceptions.EvaluateException;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public abstract class BinaryOperation implements TripleExpression {
    private TripleExpression first;
    private TripleExpression second;


    BinaryOperation(TripleExpression first, TripleExpression second) {
        this.first = first;
        this.second = second;
    }

    abstract void check(int x, int y)throws EvaluateException;
    abstract int operation(int first, int second)throws EvaluateException;

    public int evaluate(int x, int y, int z) throws EvaluateException{
        return operation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
