package com.cs3240.parsergenerator.Domain;

import java.util.Map;

public class ParseTableEntry {

	private Map<NonterminalSymbol, ParseAction> inputs;
	
	private Map<TerminalSymbol, Integer> gotos;
	
}
