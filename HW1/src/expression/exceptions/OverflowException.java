package expression.exceptions;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class OverflowException extends EvaluateException {
    public OverflowException() {
        super("overflow");
    }
}
