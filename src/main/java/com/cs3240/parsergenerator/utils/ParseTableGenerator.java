package com.cs3240.parsergenerator.utils;

import static com.cs3240.parsergenerator.Domain.Symbol.$;
import static com.cs3240.parsergenerator.Domain.Symbol.EPSILON;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cs3240.parsergenerator.Domain.Grammar;
import com.cs3240.parsergenerator.Domain.NonterminalSymbol;
import com.cs3240.parsergenerator.Domain.ParseAction;
import com.cs3240.parsergenerator.Domain.ParseTable;
import com.cs3240.parsergenerator.Domain.ParseTableEntry;
import com.cs3240.parsergenerator.Domain.Rule;
import com.cs3240.parsergenerator.Domain.Symbol;
import com.cs3240.parsergenerator.Domain.TerminalSymbol;

public class ParseTableGenerator {
	public static ParseTable generateTable(Grammar grammar) {
		ParseTable table = new ParseTable(grammar);
		first(grammar);
		follow(grammar);
		for (Rule rule : grammar.getAllRules()) {
			
			NonterminalSymbol A = rule.getLhs();
			Set<TerminalSymbol> firstA = calculateFirst(rule.getRule());
			
			for (TerminalSymbol s : firstA) {
				if (!s.equals(EPSILON)) {
					ParseAction action = new ParseAction(A, rule);
					table.add(new ParseTableEntry(A, s, action));
				}
			}
			
			if (firstA.contains(EPSILON)) {
				for (TerminalSymbol s : A.getFollow()) {
					ParseAction action = new ParseAction(A, rule);
					table.add(new ParseTableEntry(A, s, action));
				}
			}
		}
		return table;
	}
	
public static void first(Grammar grammar) {
		
		// initialize the first set for each non-terminal to an empty list
		for (NonterminalSymbol symbol : grammar.getNonTerminals()) {
			symbol.setFirst(new TreeSet<TerminalSymbol>());
		}

		boolean changes = true;

		// continue while there are changes to any First(A)
		while (changes) {
			changes = false;

			// iterate over each rule where A is on the left-hand side and X_k on the right
			for (Rule r : grammar.getAllRules()) {

				NonterminalSymbol A = r.getLhs();
				List<Symbol> rule = r.getRule();
				
				changes |= A.getFirst().addAll(calculateFirst(rule));
			}
		}
	}
	
	/**
	 * Computes the follow sets for all nonterminal symbols in the supplied grammar.
	 * The first() method should be called before this because the first sets are 
	 * used in computation of follow sets.
	 * @param grammar Grammar to compute follow sets for 
	 */
	public static void follow(Grammar grammar) {
		
		// Initialize the follow sets for all nonterminals to an empty set
		for (NonterminalSymbol symbol : grammar.getNonTerminals()) {
			symbol.setFollow(new TreeSet<TerminalSymbol>());
		}
		
		// the $ is automatically in the follow set of the start symbol
		grammar.getStartSymbol().getFollow().add($);
		
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
						
						Set<TerminalSymbol> firstXn = null;
						
						if (i == rule.size() - 1) {
							// if i=n then Xi+1...Xn = epsilon
							firstXn = new TreeSet<TerminalSymbol>();
							firstXn.add(EPSILON);
							
						} else {
							// calculate first(Xi+1...Xn)
							firstXn = calculateFirst(rule.subList(i + 1, rule.size()));
						}
						
						// add [first(Xi+1 Xi+2...Xn) - {epsilon}] to follow(Xi)
						changes |= Helper.addAllExceptFor(firstXn, ((NonterminalSymbol) Xi).getFollow(), EPSILON);
						
						// if epsilon is in first(Xi+1...Xn) then add follow(A) to follow(Xi)
						if (firstXn.contains(EPSILON)) {
							
							changes |= ((NonterminalSymbol) Xi).getFollow().addAll(A.getFollow());
						}
					}
				}
			}
		}
	}
	
	private static Set<TerminalSymbol> calculateFirst(List<Symbol> alpha) {
		int k = 0;
		Set<TerminalSymbol> first = new TreeSet<TerminalSymbol>();
		
		boolean cont = true;
		
		while (k < alpha.size() && cont) {
			Symbol Xk = alpha.get(k);
			k++;
			
			// First(A) contains First(Xi) - {epsilon}
			Helper.addAllExceptFor(Xk.getFirst(), first, EPSILON);
			if (!Xk.getFirst().contains(EPSILON)) {
				cont = false;
			}
		}
		// cont will be true here if First(Xi) contains epsilon for all Xi in the rule A->X1 X2... Xn 
		if (cont) {
			first.add(EPSILON);
		}
		return first;
	}

}
