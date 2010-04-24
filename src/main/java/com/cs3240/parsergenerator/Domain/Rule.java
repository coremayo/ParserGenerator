package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Rule {
	
	private NonterminalSymbol lhs;
	
	private List<Symbol> theRule;
	
	public Rule(List<Symbol> rule) {
		theRule = rule;
	}
	
	public Rule(Symbol...rule) {
		this.theRule = new ArrayList<Symbol>(rule.length);
		for (Symbol s : rule) {
			theRule.add(s);
		}
	}
	
	public Rule() {
		this(new ArrayList<Symbol>());
	}
	
	public void setRule(List<Symbol> rule) {
		this.theRule = rule;
	}
	
	public List<Symbol> getRule() {
		return theRule;
	}
	
	public NonterminalSymbol getLhs() {
		return lhs;
	}

	public void setLhs(NonterminalSymbol lhs) {
		this.lhs = lhs;
	}

	public Symbol get(final int i) {
		return theRule.get(i);
	}
	
	public void remove(final int i) {
		theRule.remove(i);
	}
	
	public void addRuleToFront(final Rule rule) {
		//ensures ordering correctly, so that if we're adding Bb to this rule, it
		//adds b to beginning first, then adds B to the beginning of that.
		for (int i = rule.getRule().size() - 1; i >= 0; i--) {
			theRule.add(0, rule.get(i));			
		}
	}
	
	public void add(final Symbol s) {
		theRule.add(s);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		ListIterator<Symbol> it = theRule.listIterator();
		while (it.hasNext()) {
			builder.append(it.next());
			if (it.hasNext()) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Rule ? theRule.equals(((Rule)o).theRule) : false;
	}
	
	@Override
	public int hashCode() {
		return theRule.hashCode();
	}

}
