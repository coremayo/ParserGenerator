package com.cs3240.parsergenerator.Domain;

import java.util.List;

/**
 * @author Bobby
 * The Nonterminal Symbol has a name, but no value.  
 * It is specific because of how grammar works.
 */
public class NonterminalSymbol extends Symbol {
	
	private boolean nullable;
	
	private List<TerminalSymbol> first;
	
	private List<TerminalSymbol> follow;
	
	public NonterminalSymbol(final String name) {
		this.setName(name);
	}

	/**
	 * A nonterminal is nullable if there existst a derivation<br>
	 * A ->* empty
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * A nonterminal is nullable if there existst a derivation<br>
	 * A ->* empty
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public List<TerminalSymbol> getFirst() {
		return first;
	}

	public void setFirst(List<TerminalSymbol> first) {
		this.first = first;
	}

	public List<TerminalSymbol> getFollow() {
		return follow;
	}

	public void setFollow(List<TerminalSymbol> follow) {
		this.follow = follow;
	}

}
