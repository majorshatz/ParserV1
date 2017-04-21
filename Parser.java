import java.io.*;
import java.util.*;



public class Parser
{
    FileInputStream url;
    Deque<token> tokens;
    ArrayList<Deque<token>> list;
    private lexer lex;
    Parser(FileInputStream urlInput) throws FileNotFoundException
    {
        this.url = urlInput;
    }

    // This function will keep receiving the tokens
    // until the stream closes
    // it also calls the appropiate function
    // for a received token
    public void grabLineCode()
    {
            lexer lex= new lexer(this.url);
            int LineNum = 1;
            token tok = lex.getNextToken();
            tokens.push(tok);
            while (lex.fp.is_open()) {//make sure input is there from file/scanner            what is the fp? I know this is supposed to be a boolean if lexer/file is open.

            if (tokens.getLast().getLexeme() == "if") {
            foundIF();

            }
            else if (tokens.peekLast().getLexeme() == "then") {
            foundTHEN();
            }
            else if (tokens.peekLast().getLexeme() == "while") {
            foundWHILE();
            }
            else if (tokens.peekLast().getLexeme() == "do") {
            foundDO();
            }
            else if (tokens.peekLast().getLexeme() == "end") {
            foudnEND();
            }


            else if (tokens.peekLast().getToken() == "ID") {
            foundID();
            }


            // Checking for new LINE
            tok = lex.getNextToken();
            if (tokens.peekLast().getLineNum() < tok.getLineNum()) {
            tokens.addLast(tok);
            newLine();
            }
            else {
            tokens.addLast(tok);
            }


            }

            }

            public void newLine() {
            //Token that belongs to the new line
            token tmp = tokens.peekLast();
            tokens.removeLast();




            // checking special cases
            if (tmp.getLexeme() == "end") {
            tokens.addLast(tmp);
            foudnEND();
            }
            else if (tmp.getLexeme() == "do") {
            foundDO();
            }
            else if (tmp.getLexeme() == "if") {
            list.add(tokens);                    //used add because it is an Array List****************
            tokens.clear();
            tokens.addLast(tmp);
            foundIF();
            }
            else if (tmp.getToken() == "COM") {
            tokens.clear();
            tokens.addLast(tmp);
            foundCOMMENT();
            }

            // making sure there are no open/closed parenthesis or curly brackets
            else if (tokens.peekLast().getToken() != "LP" && tokens.peekLast().getToken() != "LC" && tokens.peekLast().getToken() != "RP") {

            tokens.addLast(new token("SEMI", ";", tokens.peekLast().getLineNum()));  // Make sure correct******************
            list.add(tokens);

            // Clearing the queue for the new line of the source code
            tokens.clear();
            //Pushing the first token of the new line
            tokens.addLast(tmp);

            }

            }

    // function for when a comment is found
            public void foundCOMMENT() {           //Is this really needed? Comments don't get executed******************

            token tmp = tokens.peekLast();
            tokens.removeLast();


            String st = tmp.getLexeme();
            st.erase(0, 2);
            String cm = "\t//";
            cm += st;
            tmp.setLexeme(cm);
            tokens.addLast(tmp);
            }

    // function for when an END is found
            public void foudnEND() {

            token tmp = new token("RC", "}", tokens.peekLast().getLineNum());
            if (tokens.peekLast().getLexeme() == "end") {

            tokens.removeLast();
            }
            list.add(tokens);
            tokens.clear();
            tokens.addLast(tmp);

            }

    // function for when a  while loop is found
            public void foundWHILE() {

            tokens.addLast(new token("LP", "(", tokens.peekLast().getLineNum()));

            }

    // function for when a DO is found
            public void foundDO() {

            tokens.removeLast();
            tokens.addLast(new token("RP", ")", tokens.peekLast().getLineNum()));  //Not sure if peek Last is correct************ may have to be a pick and remove one
            tokens.addLast(new token("LC", "{", tokens.peekLast().getLineNum()));

            }

    // function for when an IF is found
            public void foundIF() {

            tokens.addLast(new token("LP", "(", tokens.peekLast().getLineNum()));

            }
    // function for when an THEN is found
            public void foundTHEN() {

            token tmp = tokens.peekLast();
            tokens.removeLast();
            tokens.addLast(new token("RP", ")", tmp.getLineNum()));
            tokens.addLast(new token("LC", "{", tmp.getLineNum()));
            list.add(tokens);

            }

    // function for when an comma is found
            public void foundCOMMA() {

            if (tokens.peekFirst().getLexeme() != "local" || tokens.peekFirst().getLexeme() != "type") {

            System.out.print("ERROR ON LINE:" + tokens.peekFirst().getLineNum() + ",  define kind of variable");
            }
            }



    // function for when an variable is found
            public void foundID() {


            boolean search = false;

            if (tokens.peekFirst().getLexeme() == "type") {

            tokens.peekFirst().setLexeme("auto");

            return;
            }

            // performing the search if the defition of the variable is not within the same line
            else {
            for (int i = 0; i < list.size(); i++) {

            search = ScanQUEUE(tokens.peekLast().getLexeme(), list.get(i));
            }

            // Throw an error if the search return false
            if (!search) {
            System.out.print("Error in line " + tokens.peekLast().getLineNum() + ": variable '" +  tokens.peekLast().getLexeme() + "' is not defined" );
            }
            }



            }

            public void foundSM(){
            token tmp = tokens.peekLast();
            tokens.removeLast();
            if(tokens.peekLast().getLexeme() == "+")
            {
            boolean search = false;
            for (int i = 0; i < list.size()-1; i++)
            {
            search = ScanQUEUE("+", list.get(i));
            }
            if (!search)
            {
            System.out.print("Error in line" + tokens.peekLast().getLineNum() + " variable is not defined");
            }


            tokens.addLast(tmp);

            }
            if(tokens.peekFirst().getLexeme() != "ID" || tokens.peekFirst().getLexeme() != "NUM")
            {
            System.out.print("Error, Number or ID expected at: " + tokens.peekLast().getLineNum() );
            }
            }

            public void foundMUL(){
            token tmp = tokens.peekLast();
            tokens.removeLast();
            if(tokens.peekLast().getLexeme() == "*")
            {
            boolean search = false;
            for (int i = 0; i < list.size()-1; i++)
            {
            search = ScanQUEUE(tokens.peekLast().getLexeme(), list[i]);
            }
            if (!search)
            {
            System.out.print("Error in line" + tokens.peekLast().getLineNum() + " variable is not defined");
            }
            tokens.addLast(tmp);

            }
            if(tokens.peekFirst().getLexeme() != "ID" || tokens.peekFirst().getLexeme() != "NUM")
            {
            System.out.print("Error, Number or ID expected at: " + tokens.peekLast().getLineNum());
            }

            }
            public void foundSUB(){
            token tmp = tokens.peekLast();
            tokens.addLast(tmp);
            if(tokens.peekLast().getLexeme() == "-")
            {
            boolean search = false;
            for (int i = 0; i < list.size()-1; i++)
            {
            search = ScanQUEUE("-", list.get(i));//changed list[i] to list.get(i)***************
            }
            if (!search)
            {
            System.out.print("Error in line"+ tokens.peekLast().getLineNum() + " variable is not defined");
            }
            tokens.addLast(tmp);

            }
            if(tokens.peekFirst().getLexeme() != "ID" || tokens.peekFirst().getLexeme() != "NUM")
            {
            System.out.print("Error, Number or ID expected at: " + tokens.peekLast().getLineNum());
            }

            }


            public void foundDIV(){
            token tmp = tokens.peekLast();
            tokens.removeLast();
            if(tokens.peekLast().getLexeme() == "/")
            {
            boolean search = false;
            for (int i = 0; i < list.size()-1; i++)
            {
            search = ScanQUEUE("/", list.get(i));
            }
            if (!search)
            {
            System.out.print("Error in line" + tokens.peekLast().getLineNum() + " variable is not defined");
            }
            tokens.removeLast();//had tmp in here

            }
            if(tokens.peekFirst().getLexeme() != "ID" || tokens.peekFirst().getLexeme() != "NUM")
            {
            System.out.print("Error, Number or ID expected at: " + tokens.peekLast().getLineNum());
            }

            }




    // Searches a deque for a specific token
            public boolean ScanQUEUE(String var, Deque<token> deq) {


            // using a lambda function to search the
            // since we are searching a list of deques of tokens
            // and looking for a specific string

            auto pred = [var]( Token & item) {
            return item.getLexeme() == var;
            };
            auto x = std::find_if(deq.begin(), deq.end(), pred) != deq.end();

            return x;
            }



}