package parser;

import exceptions.*;
import operations.Operation;

import java.util.EnumSet;
import java.util.Set;

import static exceptions.ParsingException.errorMessage;

/**
 * @author Nikolay Yarlychenko
 */
public class Tokenizer<T> {
    private String expression;
    private int index;
    private int balance;
    private T value;
    private String varName;
    private Set<Token> defaultOperations = EnumSet.of(Token.ADD, Token.MUL, Token.DIV, Token.SUB);
    private Set<Token> specialOperations = EnumSet.of(Token.ABS, Token.SQUARE, Token.MOD);
    private Token curToken;
    private Operation<T> operation;

    Tokenizer(String expression, Operation<T> operation) {
        this.expression = expression;
        this.index = 0;
        this.curToken = Token.BEGIN;
        this.balance = 0;
        this.operation = operation;
    }

    T getValue() {
        return value;
    }

    String getVarName() {
        return varName;
    }

    Token getCurToken() {
        return curToken;
    }

    void getNextToken() throws ParsingException, OverflowException {
        nextToken();
    }

    int getIndex() {
        return index;
    }

    public String getExpression() {
        return expression;
    }

    private void skipWhiteSpaces() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void checkForOperand() throws MissingArgumentException {
        if (defaultOperations.contains(curToken)) {
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
        while (index < expression.length() && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.')) {
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
                            value = operation.toCurrentMode("-" + temp);
                        } catch (NumberFormatException e) {
                            throw new OverflowException();
                        } catch (EvaluateException e) {
                            e.printStackTrace();
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
                if (defaultOperations.contains(curToken) || curToken == Token.OPEN_BRACE) {
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
                        value = operation.toCurrentMode(curNumber);
                    } catch (NumberFormatException e) {
                        throw new OverflowException();
                    } catch (EvaluateException e) {
                        e.printStackTrace();
                    }
                    curToken = Token.NUMBER;
                    index--;
                } else if (ch == 'x' || ch == 'y' || ch == 'z') {
                    varName = String.valueOf(ch);
                    curToken = Token.VARIABLE;
                } else if (checkForSpecialOperations()) {
                    break;
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


    private boolean checkForSpecialOperations() {
        for (Token x : specialOperations) {
            String s = getOperation(x);
            if (expression.startsWith(s, index)) {
                curToken = x;
                int operationLength = (s.length() - 1);
                index += operationLength;
                return true;
            }
        }
        return false;
    }

    private String getOperation(Token token) {
        switch (token) {
            case ABS:
                return "abs";
            case SQUARE:
                return "square";
            case MOD:
                return "mod";
            default:
                return "";
        }
    }
}


