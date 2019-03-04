package expression.exceptions;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class MissingOperatorException extends ParsingException {
    public MissingOperatorException(String exception) {
        super(exception);
    }
}
