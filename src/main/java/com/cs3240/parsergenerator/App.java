package com.cs3240.parsergenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException
    {
    	Scanner scan = new Scanner(new File(args[0]));
    	
    	StringBuffer output = new StringBuffer();
    	
    	while (scan.hasNext()) {
    		String token = scan.next("\\s*");
    		output.append(LexicalClass.parseToken(token).toString() + " ");
    	}
    	
    	System.out.println(output.toString());
    }
}
