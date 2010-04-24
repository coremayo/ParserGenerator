package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Bobby
 * The master class for the Grammar.  This will keep a Mapping of all the Symbols 
 * and their names.  This will also be able to get out all the rules for each 
 * non-terminal by keeping a mapping of nonterminal symbols to rules.
 * 
 * TODO Make it so that a nonterminal can have multiple rules.
 */
public class Grammar {
	private Map<String, Symbol> mapOfSymbols;
	private Map<NonterminalSymbol, List<Rule>> rulesMap;
	private NonterminalSymbol startRule;
	
	public Grammar() {
		this.mapOfSymbols = new HashMap<String, Symbol>();
		this.rulesMap = new HashMap<NonterminalSymbol, List<Rule>>();
	}
	
	/**
	 * Adds a symbol to this grammar.
	 * @param symbol The symbol to add.
	 */
	public void addSymbol(Symbol symbol) {
		mapOfSymbols.put(symbol.getName(), symbol);
	}
	
	/**
	 * Gets the symbol from the mapOfSymbols
	 * @param name Name of Symbol to get
	 * @return the Symbol associated with the name.
	 */
	public Symbol getSymbol(String name) {
		return mapOfSymbols.get(name);
	}
	
	/**
	 * Adds a rule to the rules map.   
	 * 
	 * @param symbol A Nonterminal, since they are only symbols with rules
	 * @param rule The rule for this nonterminal
	 */
	public void addRule(NonterminalSymbol symbol, List<String> rule) {
		List<Rule> rules = rulesMap.get(symbol);
		if (rules == null) {
			rules = new ArrayList<Rule>();
		}
		rules.add(new Rule(rule));
		rulesMap.put(symbol, rules);
	}
	
	/**
	 * This gets the rule with Symbols in it out of the map. 
	 * @param symbol The nonterminal that we are getting the rule for.
	 * @return a List of Symbols representing the rule for this nonterminal.
	 */
	public List<List<Symbol>> getRule(NonterminalSymbol symbol) {
		List<Rule> rules = rulesMap.get(symbol);
		List<List<Symbol>> modifiedRules = new ArrayList<List<Symbol>>();
		for (Rule rawRule : rules) {
			List<Symbol> modifiedRule = new ArrayList<Symbol>();
			for (String s : rawRule.getRule()) {
				Symbol nextSymbol = mapOfSymbols.get(s);
				modifiedRule.add(nextSymbol);
			}
			modifiedRules.add(modifiedRule);
		}
		return modifiedRules;
	}

	public void setStartRule(NonterminalSymbol startRule) {
		this.startRule = startRule;
	}

	public NonterminalSymbol getStartRule() {
		return startRule;
	}
	
	/**
	 * This is for sake of debugging, returns in format
	 * 
	 * TERMINALS: ...
	 * NONTERMINALS: ...
	 * START: ...
	 * RULES:
	 * <>: ...
	 * <>: ...
	 * 
	 */
	public String toString() {
		//First, the symbols
		StringBuilder build = new StringBuilder();
		build.append("SYMBOLS: ");
		Set<String> symbols = (Set<String>) mapOfSymbols.keySet();
		for (String s : symbols) {
			build.append(s + " ");
		}
		build.append("\n");
		
		//Now, the Rules
		Set<NonterminalSymbol> keySetRules = rulesMap.keySet();
		build.append("RULES: \n");
		for (NonterminalSymbol sym : keySetRules) {
			List<Rule> rules = rulesMap.get(sym);
			for (Rule rule : rules) {
				build.append(sym.getName() + " : ");
				for (String s : rule.getRule()) {
					build.append(s + " ");
				}
				build.append("\n");
			}
		}
		return build.toString();
	}
	
	public void removeLeftRecursion() {
		Set<String> symKeySet = mapOfSymbols.keySet();
		for (String s : symKeySet) {
			Symbol sym = mapOfSymbols.get(s);
			if (sym instanceof NonterminalSymbol) {
				NonterminalSymbol nonTerm = (NonterminalSymbol) sym;
				leftFactor(nonTerm);
				removeSelfRecursion(nonTerm);
			}
		}
	}

	private void removeSelfRecursion(NonterminalSymbol nonTerm) {
		// TODO Auto-generated method stub
		
	}

	private void leftFactor(NonterminalSymbol nonTerm) {
		List<Rule> rules = rulesMap.get(nonTerm);
		
		
	}

	/**
	 * @return A list of all nonterminal symbols used in the grammar.
	 */
	public List<NonterminalSymbol> getNonTerminals() {
		// TODO Implement me
		return null;
	}

	public List<Rule> getRulesForNonterminal(NonterminalSymbol symbol) {
		// TODO Implement me
		return null;
	}

}
