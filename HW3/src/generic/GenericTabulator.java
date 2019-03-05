package generic;

import exceptions.EvaluateException;
import exceptions.OverflowException;
import operations.*;
import parser.Parser;
import exceptions.ParsingException;
import expression.TripleExpression;
import parser.ExpressionParser;

/**
 * @author Nikolay Yarlychenko
 */

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        return getArray(getOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Operation<?> getOperation(String mode) {
        Operation<?> result;
        switch (mode) {
            case ("i"):
                result = new IntegerOperation();
                break;
            case ("d"):
                result = new DoubleOperation();
                break;
            case ("bi"):
                result = new BigIntegerOperation();
                break;
            case ("l"):
                result = new LongOperation();
                break;
            case ("s"):
                result = new IntegerOperation();
                break;
            default:
                result = null;
        }

        return result;
    }

    private <T> Object[][][] getArray(Operation<T> mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {

        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        if (mode == null) {
            return res;
        }

        Parser<T> parser = new ExpressionParser(mode);
        TripleExpression<T> currentExpression;
        try {
            currentExpression = parser.parse(expression);
        } catch (ParsingException | OverflowException e) {
            e.printStackTrace();
            return res;
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        res[i - x1][j - y1][k - z1] = currentExpression.evaluate(mode.toCurrentMode(Integer.toString(i)), mode.toCurrentMode(Integer.toString(j)), mode.toCurrentMode(Integer.toString(k)));
                    } catch (EvaluateException e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return res;
    }


}
