package expression;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Variable<T> implements TripleExpression<T> {
    private String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    public T evaluate(T x, T y, T z) {
        if ("x".equals(varName)) {
            return x;
        }
        if ("y".equals(varName)) {
            return y;
        }
        return z;
    }
}
