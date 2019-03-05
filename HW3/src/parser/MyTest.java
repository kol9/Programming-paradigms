package parser;

import exceptions.EvaluateException;
import exceptions.ParsingException;
import generic.GenericTabulator;
import generic.Tabulator;

import java.util.Arrays;


/**
 * Created by Nikolay Yarlychenko on 05/12/2018
 */
public class MyTest {
    public static void main(String[] args) throws Exception {

        String s = "10";
        Tabulator tabulator = new GenericTabulator();
        final Object[][][] result;
        int x1, x2, y1, y2, z1, z2;
        x1 = -2147483648;
        x2 = -2147483648;
        y1 = -2147483648;
        y2 = -2147483648;
        z1 = -2147483648;
        z2 = -2147483648;

        result = tabulator.tabulate("b", s, x1, x2, y1, y2, z1, z2);
        System.out.println(Arrays.deepToString(result));
    }
}

