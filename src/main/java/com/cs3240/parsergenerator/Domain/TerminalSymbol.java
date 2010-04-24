package com.cs3240.parsergenerator.Domain;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Bobby
 * This is the terminal symbol.
 */
public class TerminalSymbol extends Symbol {
	
	public TerminalSymbol(final String name) {
		this.setName(name);
	}

	@Override
	public Set<TerminalSymbol> getFirst() {
		Set<TerminalSymbol> ret = new TreeSet<TerminalSymbol>();
		ret.add(this);
		return ret;
	}
}
