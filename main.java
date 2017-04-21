/**
 *
 *
 */

import java.io.*;
import java.util.Scanner;
public class main {
    static String tok;

    public static void main(String[] args){


        File file = new File("Sample.lua");
        try
        {
            Scanner scan = new Scanner(file);
            for (int i = 0; scan.hasNextLine(); i++) {
                tok = scan.nextLine();
                System.out.print(tok);
            }
        }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }


    }
}
