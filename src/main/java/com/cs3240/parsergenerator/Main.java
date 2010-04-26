package com.cs3240.parsergenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cs3240.parsergenerator.Domain.Grammar;
import com.cs3240.parsergenerator.Domain.NonterminalSymbol;
import com.cs3240.parsergenerator.Domain.ParseTable;
import com.cs3240.parsergenerator.Domain.Rule;
import com.cs3240.parsergenerator.Domain.Symbol;
import com.cs3240.parsergenerator.Domain.TerminalSymbol;
import com.cs3240.parsergenerator.utils.Driver;

public class Main {

	public static void main(String[] args) {
		
		System.out.println(
				"Please type in grammer, with multiple rules per line separated by the | character \n" +
				"in the format <a> : b <c> | c where a and c are nonterminals and b is a terminal. \n" +
				"Hit enter twice to finish.");
		
		Grammar grammar = new Grammar();
		
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			
			String line = scan.nextLine();
			if (line.isEmpty()) {
				break;
			}
			
			//TODO validate the line's content before continuing?
			
			String[] tokens = line.split(" ");
			NonterminalSymbol lhs = new NonterminalSymbol(tokens[0]);
			List<Symbol> currentRule = new ArrayList<Symbol>();
			
			// tokens[0] = left hand side of rules
			// tokens[1] = : character
			for (int i = 2; i < tokens.length; i++) {
				
				// | means end of the current rule
				if (tokens[i].equals("|")) {
					Rule rule = new Rule(currentRule);
					currentRule.clear();
					rule.setLhs(lhs);
					grammar.addRule(lhs, rule);
					if (grammar.getStartRule() == null) {
						grammar.setStartRule(lhs);
					}
					
				} else {
					Symbol symbol = null;
					
					// two cases here, a terminal or a non-terminal
					if (tokens[i].charAt(0) == '<') {
						// we have a non-terminal
						symbol = new NonterminalSymbol(tokens[i]);
						
					} else {
						// it's a terminal
						symbol = new TerminalSymbol(tokens[i]);
					}
					currentRule.add(symbol);
				}
			}
			Rule r = new Rule(currentRule);
			r.setLhs(lhs);
			grammar.addRule(lhs, r);
			if (grammar.getStartRule() == null) {
				grammar.setStartRule(lhs);
			}
		}
		
		grammar.removeLeftRecursion();
		ParseTable pt = new ParseTable(grammar);
		
		boolean cont = true;
		while  (cont) {
			System.out.println("Enter a line to check acceptance by the supplied grammar.");
			
			if (Driver.parse(pt, scan.nextLine())) {
				System.out.println("Successful parse!");
			} else {
				System.out.println("Failed parse");
			}

			while (true) {
				System.out.print("Would you like to try another? (y/n) [y]");
				String line = scan.nextLine();
				if (line.isEmpty()|| line.toLowerCase().equals("y")) {
					cont = true;
					break;
				} else if (line.toLowerCase().equals("n")) {
					cont = false;
					break;
				} else {
					System.out.println("Invalid answer: \"" + line + "\"");
				}
			}
		}
	}
}
