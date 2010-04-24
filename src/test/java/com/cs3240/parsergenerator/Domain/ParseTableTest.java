package com.cs3240.parsergenerator.Domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParseTableTest {

	Grammar grammar;
	NonterminalSymbol exp = new NonterminalSymbol("<exp>");
	NonterminalSymbol expPrime = new NonterminalSymbol("<exp'>");
	NonterminalSymbol addop = new NonterminalSymbol("<addop>");
	NonterminalSymbol term = new NonterminalSymbol("<term>");
	NonterminalSymbol termPrime = new NonterminalSymbol("<term'>");
	NonterminalSymbol mulop = new NonterminalSymbol("<mulop>");
	NonterminalSymbol factor = new NonterminalSymbol("<factor>");
	TerminalSymbol plus = new TerminalSymbol("+");
	TerminalSymbol minus = new TerminalSymbol("-");
	TerminalSymbol multiply = new TerminalSymbol("*");
	TerminalSymbol leftPar = new TerminalSymbol("(");
	TerminalSymbol rightPar = new TerminalSymbol(")");
	TerminalSymbol number = new TerminalSymbol("number");
	
	private ParseTable parseTable;
	
	public ParseTableTest() {
		/*
		 * TERMINAL SYMBOLS (5): NUMBER ID LEFTPAR RIGHTPAR SEMICOLON 
		 * NONTERMINAL SYMBOLS (5): <lexp> <atom> <lexp-seq> <list> <lexp-seq>Prime 
		 * RULES: 
		 * <lexp-seq>Prime : <lexp> <lexp-seq>Prime 
		 * <lexp-seq>Prime : . 
		 * <lexp-seq> : <list> <lexp-seq>Prime 
		 * <lexp-seq> : NUMBER <lexp-seq>Prime 
		 * <lexp-seq> : ID <lexp-seq>Prime 
		 * <list> : LEFTPAR <lexp-seq> RIGHTPAR 
		 * <atom> : NUMBER 
		 * <atom> : ID 
		 * <lexp> : <atom> 
		 * <lexp> : <list> 
		 */
		Grammar grammar = new Grammar();
		grammar.setStartRule(exp);
		grammar.addRule(exp, new Rule(term, expPrime));
		grammar.addRule(expPrime, new Rule(addop, term, expPrime));
		grammar.addRule(expPrime, new Rule(Symbol.EPSILON));
		grammar.addRule(addop, new Rule(plus));
		grammar.addRule(addop, new Rule(minus));
		grammar.addRule(term, new Rule(factor, termPrime));
		grammar.addRule(termPrime, new Rule(mulop, factor, termPrime));
		grammar.addRule(termPrime, new Rule(Symbol.EPSILON));
		grammar.addRule(mulop, new Rule(multiply));
		grammar.addRule(factor, new Rule(leftPar, exp, rightPar));
		grammar.addRule(factor, new Rule(number));
		
		this.parseTable = new ParseTable(grammar);
	}

	@Test
	public void testParseTable() {
		
		ParseTable expected = new ParseTable();
		
		fail("Not yet implemented");
	}

}
