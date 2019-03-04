package generic;

import exceptions.EvaluateException;
import exceptions.OverflowException;
import operations.BigIntegerOperation;
import operations.DoubleOperation;
import operations.IntegerOperation;
import operations.Operation;
import parser.Parser;
import exceptions.ParsingException;
import expression.TripleExpression;
import parser.ExpressionParser;

/**
 * @author Nikolay Yarlychenko
 */
public class GenericTabulator implements Tabulator {


    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return getArray(getOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Operation<?> getOperation(String mode) throws Exception {
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
            default:
                result = null;
        }
        if (result == null) {
            throw new Exception();
        }
        return result;
    }

    private <T> Object[][][] getArray(Operation<T> currentMode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {

        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        
        Parser<T> parser = new ExpressionParser(currentMode);
        TripleExpression<T> exp;
        try {
            exp = parser.parse(expression);
        } catch (ParsingException | OverflowException e) {
            return res;
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        res[i - x1][j - y1][k - z1] = exp.evaluate(currentMode.parseNumber(Integer.toString(i)), currentMode.parseNumber(Integer.toString(j)), currentMode.parseNumber(Integer.toString(k)));
                    } catch (EvaluateException e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return res;
    }


}
