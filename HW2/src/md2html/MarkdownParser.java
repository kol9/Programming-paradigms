package md2html;

/**
 * Created by Nikolay Yarlychenko on 17/02/2019
 */
class MarkdownParser {
    private final MarkdownSource source;
    private StringBuilder result = new StringBuilder();

    MarkdownParser(MarkdownSource source) {
        this.source = source;
    }

    String parse() throws MarkdownException {
        source.nextChar();
        parseParagraphs();
        return result.toString();
    }

    private void parseParagraphs() throws MarkdownException {
        if (test(MarkdownSource.END)) {
            return;
        }
        skipEmptyLines();

        if (test(MarkdownSource.END)) {
            return;
        }
        final String headline = headline();
        String tag;
        if (headline.isEmpty() || !Character.isDigit(headline.charAt(0))) {
            tag = "p";
            startTag(tag, result);
            result.append(headline);
        } else {
            tag = "h" + headline;
            startTag(tag, result);
        }
        result.append(parseText("", "", false));
        endTag(tag, result);
        result.append('\n');
        parseParagraphs();
    }

    private String parseText(String s, String tag, boolean isText) throws MarkdownException {
        StringBuilder sb1 = new StringBuilder();
        boolean flag = false;
        while (!test(MarkdownSource.END)) {
            if (isMarkup(s)) {
                source.nextChar();
                if (!tag.isEmpty()) {
                    endTag(tag, sb1);
                }
                break;
            }
            if (isText) {
                sb1.append(source.getChar());
                source.nextChar();
                continue;
            }

            if (isNewParagraph()) {
                break;
            } else if (test(MarkdownSource.END)) {
                break;
            } else if (sb1.length() != 0 && source.getPrev() == '\n' && !test('\n')) {
                System.out.println(source.getChar());
                sb1.append('\n');
            }
            if (isMarkup(s)) {
                source.nextChar();
                if (!tag.isEmpty()) {
                    endTag(tag, sb1);
                }
                break;
            }

            if (test('\\')) {
                source.nextChar();
                if (test('_') || test('*')) {
                    sb1.append(source.getChar());
                    source.nextChar();
                }
            } else if (test('<') || test('>') || test('&')) {
                sb1.append(parseSpecialSymbols());
                source.nextChar();
            } else if (test('`')) {
                startTag("code", sb1);
                source.nextChar();
                sb1.append(parseText("`", "code", false));
            } else if (test('~')) {
                startTag("mark", sb1);
                source.nextChar();
                sb1.append(parseText("~", "mark", false));
            } else if (test('*') || test('_')) {
                char oldChar = source.getChar();
                source.nextChar();
                if (test(oldChar)) {
                    startTag("strong", sb1);
                    sb1.append(parseText(String.valueOf(oldChar) + oldChar, "strong", false));
                } else if (!test(' ') && !test('\n')) {
                    String concat = parseText(String.valueOf(oldChar), "em", false);
                    if (source.getPrev() == oldChar) {
                        startTag("em", sb1);
                    }
                    sb1.append(concat);
                } else {
                    sb1.append(oldChar);
                }
            } else if (test('-')) {
                doubleMark("--", "s", sb1);
            } else if (test('+')) {
                doubleMark("++", "u", sb1);
            } else if (test('[')) {
                source.nextChar();
                String description = parseText("]", "", false);
                source.nextChar();
                String link = parseText(")", "", true);
                String start = "a href=\'" +
                        link +
                        '\'';
                startTag(start, sb1);
                sb1.append(description);
                endTag("a", sb1);
            } else if (test('!')) {
                if (testNext('[')) {
                    source.nextChar();
                    String description = parseText("]", "", true);

                    source.nextChar();
                    String link = parseText(")", "", true);
                    String img = "<img alt=\'" +
                            description +
                            "\' src=\'" +
                            link +
                            "\'>";
                    sb1.append(img);
                }

            } else {
                if (test(MarkdownSource.END)) {
                    return sb1.toString();
                }
                sb1.append(source.getChar());
                source.nextChar();
            }
        }

        return sb1.toString();
    }

    private void doubleMark(String s, String tag, StringBuilder sb) throws MarkdownException {
        if (test(s.charAt(0))) {
            if (testNext(s.charAt(1))) {
                startTag(tag, sb);
                sb.append(parseText(s, tag, false));
            } else {
                sb.append(source.getPrev());
            }
        }
    }

    private boolean isMarkup(String s) throws MarkdownException {
        if (s.length() == 1) {
            return test(s.charAt(0));
        } else if (s.length() == 2) {
            if (test(s.charAt(0))) {
                return testNext(s.charAt(1));
            }
        }
        return false;
    }


    private String parseSpecialSymbols() {
        char c = source.getChar();
        switch (c) {
            case ('<'):
                return "&lt;";
            case ('>'):
                return "&gt;";
            case ('&'):
                return "&amp;";
        }
        return "";
    }

    private boolean isNewParagraph() throws MarkdownException {
        if (test('\n')) {
            source.nextChar();
            return test('\n');
        }
        return false;
    }

    private String headline() throws MarkdownException {
        int headLineLevel = 0;
        skipEmptyLines();
        StringBuilder sb = new StringBuilder();
        while (source.getChar() == '#') {
            sb.append('#');
            headLineLevel++;
            source.nextChar();
        }

        if (headLineLevel != 0 && source.getChar() == ' ') {
            source.nextChar();
            return Integer.toString(headLineLevel);
        } else return sb.toString();
    }

    private boolean test(final char c) {
        return source.getChar() == c;
    }

    private boolean testNext(final char c) throws MarkdownException {
        return source.nextChar() == c;
    }

    private void skipEmptyLines() throws MarkdownException {
        while (source.getChar() == '\n') {
            source.nextChar();
        }
    }

    private void startTag(final String tag, StringBuilder sb) {
        sb.append('<').append(tag).append('>');
    }

    private void endTag(final String tag, StringBuilder sb) {
        sb.append("</").append(tag).append('>');
    }
}
