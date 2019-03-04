package expression;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Const implements TripleExpression {
    private Number value;

    public Const(Number value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

}
