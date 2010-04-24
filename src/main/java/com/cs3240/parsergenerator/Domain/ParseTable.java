package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseTable {

	private Map<Integer, ParseTableEntry> table;
	
	public ParseTable(Grammar grammar) {
		first(grammar);
		follow(grammar);
	}

	private void first(Grammar grammar) {
		
		// initialize the first set for each non-terminal to an empty list
		for (NonterminalSymbol symbol : grammar.getNonTerminals()) {
			symbol.setFirst(new ArrayList<TerminalSymbol>());
		}

		// find the first set for every non-terminal symbol
		for (NonterminalSymbol symbol : grammar.getNonTerminals()) {

			boolean changes = true;
			
			// continue while there are changes to any First(A)
			while (changes) {
				changes = false;

				List<Rule> productionChoices = grammar.getRulesForNonterminal(symbol);
				
				// iterate over each rule where A is on the left-hand side
				for (Rule r : productionChoices) {

					int k = 1;
					boolean cont = true;

					while (cont && k <= productionChoices.size()) {

						List<TerminalSymbol> toAdd;

						// firstXK = first(X_k)
						List<TerminalSymbol> firstXK = null;

						// firstA = first(A)
						List<TerminalSymbol> firstA = null;

						// add symbols that are in the first of the current non-terminal but not A
						toAdd = new ArrayList<TerminalSymbol>(firstXK);
						toAdd.removeAll(firstA);

						// if some rule is going to be added to A then set our changes flag
						if (toAdd.size() > 0) {
							changes = true;
							cont = false;
						}
						k++;
					}
				}
			}
		}
	}
	
	private List<Symbol> follow(Grammar grammar) {
		return null;
	}
}
