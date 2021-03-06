package com.cs3240.parsergenerator.Domain;


public class ParseTableEntry implements Comparable<ParseTableEntry> {
	private TerminalSymbol terminal;
	private ParseAction action;
	private NonterminalSymbol nonTerminal;
	
	public NonterminalSymbol getNonTerminal() {
		return nonTerminal;
	}

	public void setNonTerminal(NonterminalSymbol nonTerminal) {
		this.nonTerminal = nonTerminal;
	}

	public TerminalSymbol getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalSymbol terminal) {
		this.terminal = terminal;
	}

	public ParseAction getAction() {
		return action;
	}

	public void setAction(ParseAction action) {
		this.action = action;
	}
	
	public ParseTableEntry(NonterminalSymbol non, TerminalSymbol term, ParseAction act) {
		this.nonTerminal = non;
		this.terminal = term;
		this.action = act;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(nonTerminal);
		builder.append(", ");
		builder.append(terminal);
		builder.append(", ");
		builder.append(action);
		return builder.toString();
	}

	public ParseTableEntry() {
		this(null,null,null);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ParseTableEntry) {
			ParseTableEntry that = (ParseTableEntry) o;
			return this.nonTerminal.equals(that.nonTerminal) 
				&& this.terminal.equals(that.terminal) 
				&& this.action.equals(that.action);
		}
		return false;
	}

	@Override
	public int compareTo(ParseTableEntry that) {
		if (this.equals(that)) {
			return 0;
		}
		
		if (!this.nonTerminal.equals(that.nonTerminal)) {
			return this.nonTerminal.compareTo(that.nonTerminal);
		}
		
		if (!this.terminal.equals(that.nonTerminal)) {
			return this.terminal.compareTo(that.terminal);
		}
		
		return this.action.compareTo(that.action);
	}
}
