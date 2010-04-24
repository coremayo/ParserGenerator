package com.cs3240.parsergenerator.Domain;

import java.util.List;

/**
 * @author Bobby
 * This will be the base for the terminal and non-terminal objects.  Since
 * both of these will be used in the grammar together, I figure that they should
 * both inherit this class.  Also, they both have names, might as well add that method
 * here.
 */
public abstract class Symbol implements Comparable<Symbol> {
	
	/** Represents epsilon, or the empty symbol. */
	public static final Symbol EPSILON = new TerminalSymbol("epsilon");
	
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<TerminalSymbol> getFirst() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Symbol) {
			return name.equals(((Symbol) obj).name);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Symbol s) {
		return name.compareTo(s.name);
	}
}
