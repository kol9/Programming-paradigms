package md2html;

/**
 * Created by Nikolay Yarlychenko on 17/02/2019
 */
public class StringMarkdownSource extends MarkdownSource {
    private final String data;


    public StringMarkdownSource(final String data) throws MarkdownException {
        this.data = data + END;
    }

    @Override
    protected char readChar() {
        return data.charAt(getPos());
    }
}
