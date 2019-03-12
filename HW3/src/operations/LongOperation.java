package operations;


/**
 * @author Nikolay Yarlychenko
 */

public class LongOperation implements Operation<Long> {


    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long sub(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long mul(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long div(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long neg(Long x) {
        return -x;
    }

    @Override
    public Long abs(Long x) {
        if (x > 0) {
            return x;
        } else {
            return neg(x);
        }
    }

    public Long square(Long x) {
        return x * x;
    }

    public Long mod(Long x, Long y) {
        return x % y;
    }

    @Override
    public Long toCurrentMode(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
