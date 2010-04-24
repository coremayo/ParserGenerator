package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseTable {

	/** Maps the parse table's DFA state to its entry containing inputs and goto's. */
	private List<ParseTableEntry> table;
	
	public ParseTable(Grammar grammar) {
		first(grammar);
		follow(grammar);
	}

	private void first(Grammar grammar) {
		
		// initialize the first set for each non-terminal to an empty list
		for (NonterminalSymbol symbol : grammar.getNonTerminals()) {
			symbol.setFirst(new ArrayList<TerminalSymbol>());
		}

		boolean changes = true;

		// continue while there are changes to any First(A)
		while (changes) {
			changes = false;

			List<Rule> productionChoices = grammar.getAllRules();

			if (productionChoices != null) {

				// iterate over each rule where A is on the left-hand side and X_k on the right
				for (Rule rule : productionChoices) {

					NonterminalSymbol A = rule.getLhs();

					int k = 0;
					boolean cont = true;

					while (cont && k < productionChoices.size()) {

						// X_k is the first symbol in the rhs of the rule
						Symbol XK = rule.get(0);

						List<TerminalSymbol> toAdd;

						// firstXK = first(X_k)
						List<TerminalSymbol> firstXK = XK.getFirst();

						// firstA = first(A)
						List<TerminalSymbol> firstA = A.getFirst();

						// add symbols that are in the first set of the current rule but not A
						toAdd = new ArrayList<TerminalSymbol>(firstXK);
						toAdd.removeAll(firstA);

						// if some rule is going to be added to A then set our changes flag
						if (toAdd.size() > 0) {
							firstA.addAll(toAdd);
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
