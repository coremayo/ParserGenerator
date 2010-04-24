package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bobby
 * This is the terminal symbol.
 */
public class TerminalSymbol extends Symbol {
	
	public TerminalSymbol(final String name) {
		this.setName(name);
	}

	@Override
	public List<TerminalSymbol> getFirst() {
		List<TerminalSymbol> ret = new ArrayList<TerminalSymbol>();
		ret.add(this);
		return ret;
	}
}
