package expression.exceptions;

/**
 * Created by Nikolay Yarlychenko on 11/12/2018
 */
public class UnreservedSequenceException extends ParsingException {
    public UnreservedSequenceException(String expression) {
        super(expression);
    }
}
