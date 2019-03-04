package expression.parser;

import expression.*;
import expression.Log;
import expression.exceptions.*;

import java.util.EnumSet;
import java.util.Set;

import static expression.exceptions.ParsingException.errorMessage;

/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public class ExpressionParser implements Parser {

    private String expression;
    private int index = 0;
    private int balance;


    private enum Token {
        ABS, ADD, BEGIN, DIV, END, LOG, OPEN_BRACE,
        MAX, MIN, MUL, NEGATE, NUMBER,
        POW, CLOSE_BRACE, SQRT, SUB, VARIABLE,
        HIGH, LOW
    }

    private Set<Token> operations = EnumSet.of(Token.ADD, Token.MUL, Token.DIV, Token.SUB);

    private Token curToken = Token.BEGIN;

    private int value;
    private String varName;

    private void skipWhiteSpaces() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void checkForOperand() throws MissingArgumentException {
        if (operations.contains(curToken)) {
            throw new MissingArgumentException("Expected argument: " + errorMessage(expression, index, false));
        } else if (curToken == Token.OPEN_BRACE || curToken == Token.BEGIN) {
            throw new MissingArgumentException("Expected argument: " + errorMessage(expression, index, false));
        }

    }

    private void checkForOperator() throws MissingOperatorException {
        if (curToken == Token.CLOSE_BRACE || curToken == Token.VARIABLE || curToken == Token.NUMBER) {
            throw new MissingOperatorException("Missed operator: " + errorMessage(expression, index, false));
        }
    }

    private String getNumber() {
        int l = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        int r = index;
        index--;
        return expression.substring(l, r);
    }

    private void nextToken() throws ParsingException, OverflowException {
        skipWhiteSpaces();
        if (index >= expression.length()) {
            curToken = Token.END;
            return;
        }

        char ch = expression.charAt(index);
        switch (ch) {
            case '-':
                if (curToken == Token.NUMBER || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACE) {
                    checkForOperand();
                    curToken = Token.SUB;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingArgumentException("Expected argument:" + errorMessage(expression, index, false));
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                        String temp = getNumber();
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException e) {
                            throw new OverflowException();
                        }
                        curToken = Token.NUMBER;
                    } else {
                        curToken = Token.NEGATE;
                    }
                }
                break;
            case '+':
                checkForOperand();
                curToken = Token.ADD;
                break;
            case '*':
                checkForOperand();
                curToken = Token.MUL;
                break;
            case '/':
                checkForOperand();
                curToken = Token.DIV;
                break;
            case '(':
                checkForOperator();
                balance++;
                curToken = Token.OPEN_BRACE;
                break;
            case ')':
                if (operations.contains(curToken) || curToken == Token.OPEN_BRACE) {
                    throw new MissingArgumentException("Expected argument: " + errorMessage(expression, index, false));
                }
                balance--;
                if (balance < 0) throw new BracketsException("Wrong brackets sequence");
                curToken = Token.CLOSE_BRACE;
                break;
            default:
                if (Character.isDigit(ch)) {
                    checkForOperator();
                    String curNumber = getNumber();
                    index++;
                    try {
                        value = Integer.parseInt(curNumber);
                    } catch (NumberFormatException e) {
                        throw new OverflowException();
                    }
                    curToken = Token.NUMBER;
                    index--;
                } else if (ch == 'x' || ch == 'y' || ch == 'z') {
                    varName = String.valueOf(ch);
                    curToken = Token.VARIABLE;
                } else if (index + 3 < expression.length() && expression.substring(index, index + 4).equals("high") && !Character.isLetter(expression.charAt(index + 4))) {
                    curToken = Token.HIGH;
                    index += 3;
                } else if (index + 2 < expression.length() && expression.substring(index, index + 3).equals("low") && !Character.isLetter(expression.charAt(index + 3))) {
                    curToken = Token.LOW;
                    index += 2;
                } else if (index + 2 < expression.length() && expression.substring(index, index + 3).equals("abs")) {
                    curToken = Token.ABS;
                    index += 2;
                } else if (index + 2 < expression.length() && expression.substring(index, index + 3).equals("min")) { // в одну функцию
                    curToken = Token.MIN;
                    index += 2;
                } else if (index + 3 < expression.length() && expression.substring(index, index + 4).equals("log2")) {
                    curToken = Token.LOG;
                    index += 3;
                } else if (index + 3 < expression.length() && expression.substring(index, index + 4).equals("pow2")) {
                    curToken = Token.POW;
                    index += 3;
                } else if (index + 2 < expression.length() && expression.substring(index, index + 3).equals("max")) {
                    curToken = Token.MAX;
                    index += 2;
                } else if (index + 3 < expression.length() && expression.substring(index, index + 4).equals("sqrt")) {
                    curToken = Token.SQRT;
                    index += 3;
                } else {
                    if (index == expression.length() - 1) {
                        throw new UnknownSymbolException("Unknown symbol \'" + expression.charAt(index) + "\' : " + errorMessage(expression, index - 1, true));
                    }
                    char nextChar = expression.charAt(++index);

                    if (nextChar == ' ' || index == expression.length() - 1) {
                        throw new UnknownSymbolException("Unknown symbol \'" + expression.charAt(index - 1) + "\' : " + errorMessage(expression, index - 1, true));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(expression.charAt(index - 1));
                        while (index < expression.length() - 1 && nextChar != ' ') {
                            sb.append(nextChar);
                            nextChar = expression.charAt(++index);
                        }
                        if (expression.charAt(index) != ' ') {
                            sb.append(expression.charAt(index));
                        }
                        throw new UnreservedSequenceException("Unreserved sequence: \"" + sb.toString() + "\"");
                    }
                }
        }

        index++;
    }

    private TripleExpression unary() throws ParsingException, OverflowException {
        nextToken();
        TripleExpression res;
        switch (curToken) {
            case NUMBER:
                res = new Const(value);
                nextToken();
                break;
            case VARIABLE:
                res = new Variable(varName);
                nextToken();
                break;
            case NEGATE:
                res = new CheckedNegate(unary());
                break;
            case ABS:
                res = new Abs(unary());
                break;
            case SQRT:
                res = new Sqrt(unary());
                break;
            case LOG:
                res = new Log(unary());
                break;
            case POW:
                res = new Pow(unary());
                break;
            case HIGH:
                res = new High(unary());
                break;
            case LOW:
                res = new Low(unary());
                break;
            case OPEN_BRACE:
                res = minMax();
                if (curToken != Token.CLOSE_BRACE) {
                    throw new BracketsException("Wrong brackets sequence");
                }
                nextToken();
                break;
            default:
                throw new MissingArgumentException("Expected argument: " + errorMessage(expression, index, true));

        }
        return res;
    }

    private TripleExpression mulDiv() throws ParsingException, OverflowException {
        TripleExpression res = unary();
        do {
            switch (curToken) {
                case MUL:
                    res = new CheckedMultiply(res, unary());
                    break;
                case DIV:
                    res = new CheckedDivide(res, unary());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression addSub() throws ParsingException, OverflowException {
        TripleExpression res = mulDiv();
        do {
            switch (curToken) {
                case ADD:
                    res = new CheckedAdd(res, mulDiv());
                    break;
                case SUB:
                    res = new CheckedSubtract(res, mulDiv());
                    break;
                default:
                    return res;
            }

        } while (true);
    }

    private TripleExpression minMax() throws ParsingException, OverflowException {
        TripleExpression res = addSub();
        do {
            switch (curToken) {
                case MIN:
                    res = new Min(res, addSub());
                    break;
                case MAX:
                    res = new Max(res, addSub());
                    break;
                default:
                    return res;
            }

        } while (true);
    }


    public TripleExpression parse(String expression) throws ParsingException, OverflowException {
        index = 0;
        this.expression = expression;
        curToken = Token.BEGIN;
        balance = 0;
        return minMax();
    }

}
