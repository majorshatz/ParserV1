/**
 * CS4380 W01
 Concepts of Programming Languages
 Professor: Jose M Garrido
 Students: Juan E. Tenorio Arzola, Thomas Nguyen, Andrew Shatz
 */

import java.io.*;

public class lexer {

    private FileInputStream url;
    private BufferedReader sb;
    private char ch;
    private static char EOF = (char) (-1);
    int counter = 1;

    public lexer(FileInputStream url)
    {
        openFile(url);

        ch = read();
    }



    private void openFile(FileInputStream url){
        this.url = url;
        sb = new BufferedReader(new InputStreamReader(url));
    }



    // Reads character by character
    private char read(){
        try {
            return (char) (sb.read());
        } catch (IOException e) {
            e.printStackTrace();
            return EOF; // EOF
        }
    }

    public token getNextToken(){

        int state =1;
        int numBuffer =0;
        String alphaBuffer = "";
        boolean skipped = false;
        while(true){

            if(ch == EOF && !skipped) { // EOF

                skipped = true;
            }
            else if(skipped) {
                try {
                    sb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }



            switch (state) {

                case 1:
                    switch (ch) {
                        case ' ':
                            ch = read(); // spaces can be ignored
                            continue;
                        case '.':
                            ch = read();

                            return new token("PD", ".", counter);
                        case '+':
                            return new token("SM", "+", counter);
                        case '/':
                            return new token("DV", "/", counter);
                        case '*':
                            return new token("MP", "*", counter);
                        case '=':
                            ch = read();
                            state = 6;
                            continue;
                        case '-':
                            return new token("ST", "-", counter);
                        default:
                            state = 2;
                            continue;

                    }


                    //  The case for when a number is scanned
                case 2:
                    if (Character.isDigit(ch)) {
                        numBuffer = 0;
                        numBuffer += Character.getNumericValue(ch);

                        state = 3;

                        ch = read();
                    } else {
                        state = 4;
                    }
                    continue;

                    // Number is found by itself
                case 3:
                    if (Character.isDigit(ch)) {
                        numBuffer *= 10;
                        numBuffer += Character.getNumericValue(ch);

                        ch = read();
                    } else {
                        return new token("NUM", String.valueOf(numBuffer), counter);
                    }
                    continue;

                    // Either a keyword is found or there is an invalid input
                case 4:

                    if (Character.isAlphabetic(ch) || ch == '_') {

                        alphaBuffer = "";
                        alphaBuffer += ch;
                        state = 5;
                        ch = read();
                    } else {
                        alphaBuffer = "";
                        alphaBuffer += ch;

                        ch = read();
                        return new token("INVALID", "Invalid input: " + alphaBuffer, counter );
                    }
                    continue;

                    // Keyword is found or a variable
                case 5:

                    if (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_') {
                        alphaBuffer += ch;
                        ch = read();
                    }
                    else
                    {
                        if (alphaBuffer.equals("int") || alphaBuffer.equals("then") || alphaBuffer.equals("type") ||
                        alphaBuffer.equals("local") || alphaBuffer.equals("end") || alphaBuffer.equals("if") ||
                        alphaBuffer.equals("while") || alphaBuffer.equals("not") || alphaBuffer.equals("do") )
                        {
                            return new token("KW", alphaBuffer, counter);
                        }
                        return new token("ID", alphaBuffer, counter);
                    }

                    continue;

                    // A boolean comparison is found or an assignment
                case 6:
                    if (ch == '=') {
                        ch = read();
                        return new token("EQ", "==", counter);
                    } else {
                        return new token("AS", "=", counter );
                    }
                case 7:
                    if (ch == '(') {
                        ch = read();
                        return new token("LP", "(", counter);
                    }
                    ch = read();
                    continue;
                case 8:
                    if (ch == ')') {
                        ch = read();
                        return new token("RP", ")", counter);
                    }
                    ch = read();
                    continue;
                case 9:
                    if (ch == '\n') {
                        state = 1;
                    }
                    ch = read();
                case 10:
                    if (alphaBuffer.equals("EOS"))
                    {
                        return new token("EOS", alphaBuffer, counter);
                    }
            }
        }
    }


}