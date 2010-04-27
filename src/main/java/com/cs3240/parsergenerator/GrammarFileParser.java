package com.cs3240.parsergenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;

import com.cs3240.parsergenerator.Domain.Grammar;
import com.cs3240.parsergenerator.Domain.NonterminalSymbol;
import com.cs3240.parsergenerator.Domain.Rule;
import com.cs3240.parsergenerator.Domain.Symbol;
import com.cs3240.parsergenerator.Domain.TerminalSymbol;

public final class GrammarFileParser {
	
	public static Grammar parse(final String fileName) throws IOException {
		File file = new File(fileName);
		return parse(file);
	}
	
	public static Grammar parse(final File file) throws IOException {
		Scanner grammarScanner = new Scanner(file);
    	Grammar myGrammar = new Grammar();
    	boolean isDefiningRules = false;
    	String[] lineSplit = grammarScanner.nextLine().split(" ");
    	//Switching between Tokens, Non-terminals, Start, and Rules
    	if (lineSplit[0].trim().equals("%Tokens")) {
    		for (int i = 1; i < lineSplit.length; i++) {
    			if (lineSplit[i].trim().isEmpty()) {
    				continue;
    			}
    			TerminalSymbol symbol = new TerminalSymbol(lineSplit[i].trim());
    			myGrammar.addSymbol(symbol);
    		}
    	} else {
    		throw new IOException("This file is not in the proper format");
    	}
    	lineSplit = grammarScanner.nextLine().split(" ");
    	if (lineSplit[0].trim().equals("%Non-terminals")) {
    		for (int i = 1; i < lineSplit.length; i++) {
    			if (lineSplit[i].trim().isEmpty()) {
    				continue;
    			}
    			NonterminalSymbol symbol = new NonterminalSymbol(lineSplit[i].trim());
    			myGrammar.addSymbol(symbol);
    		}
    	} else {
    		throw new IOException("This file is not in the proper format");
    	}
    	lineSplit = grammarScanner.nextLine().split(" ");
    	if (lineSplit[0].trim().equals("%Start")) {
    		myGrammar.setStartSymbol((NonterminalSymbol) myGrammar.getSymbol(lineSplit[1].trim()));
    	} else {
    		throw new IOException("This file is not in the proper format");
    	}
    	lineSplit = grammarScanner.nextLine().split(" ");
    	if (lineSplit[0].trim().equals("%Rules")) {
    		isDefiningRules = true;
    	} else {
    		throw new IOException("This file is not in the proper format");
    	} 
    	while (grammarScanner.hasNextLine()) {
    		lineSplit = grammarScanner.nextLine().split(" ");
			if (isDefiningRules) {
				NonterminalSymbol symbol =
					(NonterminalSymbol) myGrammar.getSymbol(lineSplit[0].trim());
				List<Symbol> rule = new ArrayList<Symbol>();
				for (int i = 2; i < lineSplit.length; i++) {
					if (lineSplit[i].trim().isEmpty()) {
    					continue;
    				}
					if (lineSplit[i].trim().equals("|")) {
						myGrammar.addRule(new Rule(symbol, rule));
						rule = new ArrayList<Symbol>();
						continue;
					}
					rule.add(myGrammar.getSymbol(lineSplit[i].trim()));
				}
				myGrammar.addRule(new Rule(symbol, rule));
			} 
		}
    		
    	
    	return myGrammar;
	}
	
}