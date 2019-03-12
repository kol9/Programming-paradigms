package parser;

/**
 * @author Nikolay Yarlychenko
 */
enum Token {
    BEGIN, END, NUMBER, VARIABLE,
    ADD, SUB, MUL, DIV, NEGATE,
    OPEN_BRACE, CLOSE_BRACE,
    ABS, SQUARE, MOD
}
