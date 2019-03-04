package md2html;

/**
 * Created by Nikolay Yarlychenko on 17/02/2019
 */
public class MarkdownException extends Throwable {
    private final int pos;
    private final int line;

    MarkdownException(final int line, final int pos, final String message) {
        super(message);
        this.line = line;
        this.pos = pos;
    }

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }
}

