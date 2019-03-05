package expression;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Const<T> implements TripleExpression<T> {
    private T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }
}
