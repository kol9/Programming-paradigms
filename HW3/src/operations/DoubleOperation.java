package operations;


/**
 * @author Nikolay Yarlychenko
 */
public class DoubleOperation implements Operation<Double> {

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double neg(Double x) {
        return -x;
    }

    @Override
    public Double parseNumber(String s) {
        return Double.parseDouble(s);
    }
}
