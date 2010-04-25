package com.cs3240.parsergenerator.Domain;


public class ParseTableEntry {
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

	public ParseTableEntry() {
		this(null,null,null);
	}
	
}
