package com.cs3240.parsergenerator.Domain;

/**
 * @author Bobby
 * The Nonterminal Symbol has a name, but no value.  
 * It is specific because of how grammar works.
 */
public class NonterminalSymbol extends Symbol {
	
	public NonterminalSymbol(final String name) {
		this.setName(name);
	}

}
