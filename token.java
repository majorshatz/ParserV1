/**
 * CS4380 W01
 Concepts of Programming Languages
 Professor: Jose M Garrido
 Students: Juan E. Tenorio Arzola, Thomas Nguyen, Andrew Shatz
 */

public class token {
    private String lexeme;

    private String token;
    private int line;
    private boolean closed = false;


    public token(String token, String lexer, int line) {
        this.lexeme = lexer;
        this.token = token;
        this.line = line;
    }

    public token(String token, String lexer, boolean closed) {
        this.lexeme = lexer;
        this.token = token;
        this.closed = closed;
    }

    public int line() {
        return line;
    }

    boolean closed() {
        return closed;
    }

    public void setBool(boolean value) {

        closed = value;

    }

    public void setLexeme(String value) {

        lexeme = value;

    }

    public void setToken(String value) {

        token = value;

    }

    public boolean getBool() {

        return closed;
    }

    public String getLexeme() {

        return lexeme;

    }

    public String getToken() {

        return token;
    }

    public int getLineNum() {

        return line;
    }
}

