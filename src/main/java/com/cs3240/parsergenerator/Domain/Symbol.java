package com.cs3240.parsergenerator.Domain;

import java.util.List;

/**
 * @author Bobby
 * This will be the base for the terminal and non-terminal objects.  Since
 * both of these will be used in the grammar together, I figure that they should
 * both inherit this class.  Also, they both have names, might as well add that method
 * here.
 */
public abstract class Symbol {
	
	/** Represents epsilon, or the empty symbol. */
	public static final Symbol EPSILON = new NonterminalSymbol("epsilon");
	
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public boolean equals(Object obj) {
		return name.equals(obj);
	}

	public abstract List<TerminalSymbol> getFirst();

}
