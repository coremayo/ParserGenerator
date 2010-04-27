package com.cs3240.parsergenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.cs3240.parsergenerator.Domain.Grammar;
import com.cs3240.parsergenerator.Domain.ParseTable;
import com.cs3240.parsergenerator.exceptions.InvalidSyntaxException;
import com.cs3240.parsergenerator.utils.Driver;
import com.cs3240.parsergenerator.utils.ParseTableGenerator;

public class Main {

    @Option(name="-g",
            usage="File containing grammar specification",
            aliases={"--grammar"})
    private File grammarFile;

    @Option(name="-i",
            usage="File containing input to check against the supplied grammar." +
                  "If this is not supplied, then the parse table is written out to " +
                  "the specified filename.",
            aliases={"--input"})
    private File inputFile;

    @Option(name="-o",
            usage="When a test input is not supplied, the parse table is written to this file.",
            aliases={"--output"},
            metaVar="FILE")
    private String outputFilename;
    
    @Option(name="-pt",
    		usage="File containing a parseTable to check input against, cannot be using with grammar file input",
    		aliases={"--parseTable"})
    private File parseTableFile;
    
    @Option(name="-s",
        	usage="Takes in a tiny program file, and outputs tokens to a file called <filename>-tokenized.txt",
		    aliases={"--scan"})
    private File tinyProgramFile;
    
    @Option(name="-so",
    	    usage="File to output tokenized tiny program to",
		    aliases={"--scannedOutput"})
    private String tinyTokenName;
    
    public static void main(String[] args)
    throws IOException, InvalidSyntaxException {


        Main m = new Main();
        CmdLineParser parser = new CmdLineParser(m);

        try {
            parser.parseArgument(args);
            
            if (m.tinyProgramFile != null) {
            	// user specified -s and -so
            	
            	Scanner scan = new Scanner(new BufferedReader(new FileReader(m.tinyProgramFile)));   
            	StringBuffer output = new StringBuffer();
            	
            	while (scan.hasNextLine()) {
            		String nextLine = scan.nextLine();
            		Scanner lineScan = new Scanner(nextLine);
            		while (lineScan.hasNext()) {
            			String token = lineScan.next();
            			if (token.contains(";")) {
            				String nonSemi = token.substring(0, token.length()-1);
            				String semi = token.substring(token.length()-1);
            				output.append(LexicalClass.parseToken(nonSemi).toString() + " ");
            				output.append(LexicalClass.parseToken(semi).toString() + " ");
            			} else {
            				output.append(LexicalClass.parseToken(token).toString() + " ");
            			}
        			}
            	}
            	try {
            		File file= new File(m.tinyTokenName);
            		FileWriter outFile = new FileWriter(file);
            		PrintWriter out = new PrintWriter(outFile);

            		out.print(output.toString());
            		out.close();

            	} catch (IOException e) {
            		throw new CmdLineException(parser, "Bad filename for tinyProgram file");
            	}
            	System.out.println("Successfully tokenized file.");
            	System.exit(0);

            } else if (m.parseTableFile != null){
            	// user specified -pt and -i
            	if (m.inputFile == null) {
            		throw new CmdLineException(parser, "The -i parameter must be used with -pt.");
            	}
            	ParseTable pt = Driver.getTableFromFile(m.parseTableFile);
                StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new FileReader(m.inputFile));
                String temp;
                while ((temp = reader.readLine()) != null) {
                    sb.append(temp);
                }

                String input = sb.toString();
                if (Driver.parse(pt, input)) {
                	System.out.println("Your input is valid with the grammar");
                }
            	
            } else if (m.inputFile != null) {
            	// -g and -i
            	if (m.grammarFile == null) {
            		throw new CmdLineException(parser, "You must specify a grammar file with the -g option.");
            	}
            	Grammar grammar = GrammarFileParser.parse(m.grammarFile);
            	grammar.removeLeftRecursion();
            	ParseTable pt = ParseTableGenerator.generateTable(grammar);
                StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new FileReader(m.inputFile));
                String temp;
                while ((temp = reader.readLine()) != null) {
                    sb.append(temp);
                }

                String input = sb.toString();
                if (Driver.parse(pt, input)) {
                	System.out.println("Your input is valid with the grammar");
                }
            	
            } else {
            	// -g and -o
            	if (m.grammarFile == null) {
            		throw new CmdLineException(parser, "You must specify a grammar file with the -g option.");
            	}
            	Grammar grammar = GrammarFileParser.parse(m.grammarFile);
            	grammar.removeLeftRecursion();
            	ParseTable pt = ParseTableGenerator.generateTable(grammar);
                Driver.outputTableToFile(pt, m.outputFilename);
            }
        } catch (CmdLineException e) {
        	System.err.println(e.getMessage());
        	parser.printUsage(System.out);
        	System.exit(1);
        }
    }
}
