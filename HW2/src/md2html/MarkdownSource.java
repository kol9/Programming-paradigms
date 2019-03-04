package md2html;

import java.io.IOException;

/**
 * Created by Nikolay Yarlychenko on 17/02/2019
 */
public abstract class MarkdownSource {
    static final char END = '\0';

    private int pos;
    private int line;
    private int posInLine;
    private char c;
    private char old;


    public int getPos() {
        return pos;
    }

    protected abstract char readChar() throws MarkdownException, IOException;

    char getChar() {
        return c;
    }

    char getPrev() {
        return old;
    }

    char nextChar() throws MarkdownException {
        try {
            if (c == '\n') {
                line++;
                posInLine = 0;
            }
            old = c;
            c = readChar();
            pos++;
            posInLine++;
            return c;
        } catch (final IOException e) {
            throw error("Source read error", e.getMessage());
        }
    }


    MarkdownException error(final String format, final Object... args) throws MarkdownException {
        return new MarkdownException(line, posInLine, String.format("%d:%d: %s", line, posInLine, String.format(format, args)));
    }
}
