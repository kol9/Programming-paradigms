package parser;

import exceptions.OverflowException;
import exceptions.ParsingException;
import expression.TripleExpression;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException, OverflowException;
}
