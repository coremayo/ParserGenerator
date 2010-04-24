package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseTable {

	/** Maps the parse table's DFA state to its entry containing inputs and goto's. */
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
		for (NonterminalSymbol A : grammar.getNonTerminals()) {

			boolean changes = true;
			
			// continue while there are changes to any First(A)
			while (changes) {
				changes = false;

				List<Rule> productionChoices = grammar.getRulesForNonterminal(A);
				
				// iterate over each rule where A is on the left-hand side and X_k on the right
				for (Rule r : productionChoices) {

					int k = 0;
					boolean cont = true;

					while (cont && k < productionChoices.size()) {
						
						Symbol XK = r.get(0);

						List<TerminalSymbol> toAdd;

						// firstXK = first(X_k)
						List<TerminalSymbol> firstXK = XK.getFirst();

						// firstA = first(A)
						List<TerminalSymbol> firstA = A.getFirst();

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
