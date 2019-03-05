package parser;

import expression.*;
import exceptions.*;
import operations.Operation;

import static exceptions.ParsingException.errorMessage;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public class ExpressionParser<T> implements Parser<T> {

    private Tokenizer<T> t;
    private Operation<T> curOperation;

    public ExpressionParser(Operation<T> curOperation) {
        this.curOperation = curOperation;
    }


    private TripleExpression<T> unary() throws ParsingException, OverflowException {
        t.getNextToken();
        TripleExpression<T> res;
        switch (t.getCurToken()) {
            case NUMBER:
                res = new Const<>(t.getValue());
                t.getNextToken();
                break;
            case VARIABLE:
                res = new Variable<>(t.getVarName());
                t.getNextToken();
                break;
            case NEGATE:
                res = new Negate<>(unary(), curOperation);
                break;
            case ABS:
                res = new Abs<>(unary(), curOperation);
                break;
            case SQARE:
                res = new Square<>(unary(), curOperation);
                break;
            case OPEN_BRACE:
                res = addSub();
                if (t.getCurToken() != Token.CLOSE_BRACE) {
                    throw new BracketsException("Wrong brackets sequence");
                }
                t.getNextToken();
                break;
            default:
                throw new MissingArgumentException("Expected argument: " + errorMessage(t.getExpression(), t.getIndex(), true));

        }
        return res;
    }

    private TripleExpression<T> mulDiv() throws ParsingException, OverflowException {
        TripleExpression<T> res = unary();
        do {
            switch (t.getCurToken()) {
                case MOD:
                    res = new Mod<>(res, unary(), curOperation);
                    break;
                case MUL:
                    res = new Multiply<>(res, unary(), curOperation);
                    break;
                case DIV:
                    res = new Divide<>(res, unary(), curOperation);
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression<T> addSub() throws ParsingException, OverflowException {
        TripleExpression<T> res = mulDiv();
        do {
            switch (t.getCurToken()) {
                case ADD:
                    res = new Add<>(res, mulDiv(), curOperation);
                    break;
                case SUB:
                    res = new Subtract<>(res, mulDiv(), curOperation);
                    break;
                default:
                    return res;
            }

        } while (true);
    }


    public TripleExpression<T> parse(String expression) throws ParsingException, OverflowException {
        t = new Tokenizer<>(expression, curOperation);
        return addSub();
    }

}
