package expression.exceptions;

import java.util.StringTokenizer;

/**
 * Created by Nikolay Yarlychenko on 10/12/2018
 */
public class ParsingException extends Exception{
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public ParsingException(String expression) {
        super(expression);
    }

    public static String errorMessage(String expression, int index, boolean isSkipped) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < index; i++) {
            sb.append(expression.charAt(i));
        }
        sb.append(ANSI_BLUE);
        sb.append("{HERE} ");
        sb.append(ANSI_RESET);
        if(!isSkipped) {
            sb.append(expression.charAt(index));
        }
        for(int i = index + 1; i < expression.length(); i++) {
            sb.append(expression.charAt(i));
        }

        return sb.toString();
    }

}
