package expression;

/**
 * Created by Nikolay Yarlychenko on 02/12/2018
 */
public class Variable implements TripleExpression {
    private String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    public int evaluate(int x, int y, int z) {
        if (varName.equals("x")) {
            return x;
        }
        if (varName.equals("y")) {
            return y;
        }
        return z;
    }
}
