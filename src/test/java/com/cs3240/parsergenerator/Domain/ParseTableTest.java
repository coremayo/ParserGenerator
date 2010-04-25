package com.cs3240.parsergenerator.Domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.cs3240.parsergenerator.utils.Helper;
import static com.cs3240.parsergenerator.Domain.Symbol.EPSILON;
import static com.cs3240.parsergenerator.Domain.Symbol.$;

public class ParseTableTest {

	Grammar grammar = new Grammar();
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
	
	public ParseTableTest() {

		grammar.addSymbol(exp);
		grammar.addSymbol(expPrime);
		grammar.addSymbol(addop);
		grammar.addSymbol(term);
		grammar.addSymbol(termPrime);
		grammar.addSymbol(mulop);
		grammar.addSymbol(factor);
		grammar.addSymbol(plus);
		grammar.addSymbol(minus);
		grammar.addSymbol(multiply);
		grammar.addSymbol(leftPar);
		grammar.addSymbol(rightPar);
		grammar.addSymbol(number);
		
		/*
		 * <exp> => <term> <exp'>
		 * <exp'> => <addop> <term> <exp'> | empty
		 * <addop> => + | -
		 * <term> => <factor> <term'>
		 * <term'> => <mulop> <factor> <term'> | empty
		 * <mulop> => *
		 * <factor> => ( <exp> ) | number
		 */
		grammar.setStartRule(exp);
		grammar.addRule(exp, new Rule(term, expPrime));
		grammar.addRule(expPrime, new Rule(addop, term, expPrime));
		grammar.addRule(expPrime, new Rule(EPSILON));
		grammar.addRule(addop, new Rule(plus));
		grammar.addRule(addop, new Rule(minus));
		grammar.addRule(term, new Rule(factor, termPrime));
		grammar.addRule(termPrime, new Rule(mulop, factor, termPrime));
		grammar.addRule(termPrime, new Rule(EPSILON));
		grammar.addRule(mulop, new Rule(multiply));
		grammar.addRule(factor, new Rule(leftPar, exp, rightPar));
		grammar.addRule(factor, new Rule(number));
		
	}
	
	@Test
	public void testFirst() {
		ParseTable.first(grammar);
		
		String message;
		Collection<TerminalSymbol> expected;
		Collection<TerminalSymbol> actual;
		
		// First(exp) = {(, number}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(leftPar);
		expected.add(number);
		actual = exp.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(exp') = {+, -, empty}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(plus);
		expected.add(minus);
		expected.add(EPSILON);
		actual = expPrime.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(addop) = {+, -}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(plus);
		expected.add(minus);
		actual = addop.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(term) = {(, number}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(leftPar);
		expected.add(number);
		actual = term.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(term') = {*, empty}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(multiply);
		expected.add(EPSILON);
		actual = termPrime.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(mulop) = {*}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(multiply);
		actual = mulop.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(factor) = {(, number}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(leftPar);
		expected.add(number);
		actual = factor.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
	}
	
	public void testFollow() {
		ParseTable.follow(grammar);
	}

	@Test
	public void testParseTable() {

//		this.parseTable = new ParseTable(grammar);
//		ParseTable expected = new ParseTable();
		
		fail("Not yet implemented");
	}

}
