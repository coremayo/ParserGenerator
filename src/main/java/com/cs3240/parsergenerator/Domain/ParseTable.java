package com.cs3240.parsergenerator.Domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.cs3240.parsergenerator.utils.Helper;

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
			symbol.setFirst(new TreeSet<TerminalSymbol>());
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

						Set<TerminalSymbol> toAdd;

						// firstXK = first(X_k)
						Set<TerminalSymbol> firstXK = XK.getFirst();

						// firstA = first(A)
						Set<TerminalSymbol> firstA = A.getFirst();

						// add symbols that are in the first set of the current rule but not A
						toAdd = new TreeSet<TerminalSymbol>(firstXK);
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
	
	/**
	 * Computes the follow sets for all nonterminal symbols in the supplied grammar.
	 * The first() method should be called before this because the first sets are 
	 * used in computation of follow sets.
	 * @param grammar Grammar to compute follow sets for 
	 */
	private void follow(Grammar grammar) {
		
		// Initialize the follow sets for all nonterminals to an empty set
		for (NonterminalSymbol symbol : grammar.getNonTerminals()) {
			symbol.setFollow(new TreeSet<TerminalSymbol>());
		}
		
		// the $ is automatically in the follow set of the start symbol
		grammar.getStartRule().getFollow().add(Symbol.$);
		
		boolean changes = true;
		
		// continue while there are changes made to any follow set
		while (changes) {
			changes = false;
			
			// iterate over each rule where A is on the left-hand side and X_k on the right
			for (Rule r : grammar.getAllRules()) {
				
				// rule = A -> X1 X2 ... Xn
				NonterminalSymbol A = r.getLhs();
				List<Symbol> rule = r.getRule();
				
				for (int i = 0; i < rule.size(); i++) {
					Symbol Xi = rule.get(i);
					
					// for each Xi that is a nonterminal 
					if (Xi instanceof NonterminalSymbol) {
						
						Set<TerminalSymbol> firstXn = new TreeSet<TerminalSymbol>();
						
						if (i == rule.size() - 1) {
							// if i=n then Xi+1...Xn = epsilon
							firstXn.add(Symbol.EPSILON);
							
						} else {
							// calculate first(Xi+1...Xn)
							int k = i + 1;
							while (k < rule.size() && firstXn.isEmpty()) {
								Symbol Xk = rule.get(k);
								Helper.addAllExceptFor(Xk.getFirst(), firstXn, Symbol.EPSILON);
								k++;
							}
							if (firstXn.isEmpty()) {
								firstXn.add(Symbol.EPSILON);
							}
						}
						
						// add [first(Xi+1 Xi+2...Xn) - {epsilon}] to follow(Xi)
						changes |= Helper.addAllExceptFor(firstXn, ((NonterminalSymbol) Xi).getFollow(), Symbol.EPSILON);
						
						// if epsilon is in first(Xi+1...Xn) then add follow(A) to follow(Xi)
						if (firstXn.contains(Symbol.EPSILON)) {
							
							changes |= ((NonterminalSymbol) Xi).getFollow().addAll(A.getFollow());
						}
					}
				}
			}
		}
	}
}
