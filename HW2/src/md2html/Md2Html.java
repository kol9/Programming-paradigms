package md2html;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Nikolay Yarlychenko on 18/02/2019
 */
public class Md2Html {
    public static void main(String[] args) {

        try {
            FileMarkdownSource input = new FileMarkdownSource(args[0]);
            String parse = new MarkdownParser(input).parse();
            try (FileWriter out = new FileWriter(args[1], StandardCharsets.UTF_8)) {
                out.write(parse);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MarkdownException e) {
            e.printStackTrace();
        }
    }
}
