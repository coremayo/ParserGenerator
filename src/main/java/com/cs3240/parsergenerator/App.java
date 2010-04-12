package com.cs3240.parsergenerator;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Scanner scan = new Scanner(System.in);
    	
    	StringBuffer output = new StringBuffer();
    	
    	while (scan.hasNext()) {
    		String token = scan.next("\\s*");
    		output.append(LexicalClass.parseToken(token).toString() + " ");
    	}
    	
    	System.out.println(output.toString());
    }
}
