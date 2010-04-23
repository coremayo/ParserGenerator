package com.cs3240.parsergenerator.Domain;

/**
 * @author Bobby
 * The Nonterminal Symbol has a name, but no value.  
 * It is specific because of how grammar works.
 */
public class NonterminalSymbol extends Symbol {
	
	private boolean nullable;
	
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

}
