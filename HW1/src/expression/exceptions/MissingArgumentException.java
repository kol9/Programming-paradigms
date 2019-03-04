package expression.exceptions;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class MissingArgumentException extends ParsingException {
    public MissingArgumentException(String exception) {
        super(exception);
    }
}
