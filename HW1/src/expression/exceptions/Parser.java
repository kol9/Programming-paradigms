package expression.exceptions;

import expression.exceptions.ParsingException;
import expression.TripleExpression;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public interface Parser {
    TripleExpression parse(String expression) throws ParsingException, OverflowException;
}
