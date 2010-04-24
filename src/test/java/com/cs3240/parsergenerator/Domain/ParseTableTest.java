/**
 * 
 */
package com.cs3240.parsergenerator.Domain;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.cs3240.parsergenerator.utils.Helper;

/**
 * @author corey
 *
 */
public class ParseTableTest {
	
	private Grammar grammar;
	NonterminalSymbol statement = new NonterminalSymbol("statement");
	NonterminalSymbol ifstmt = new NonterminalSymbol("if-stmt");
	NonterminalSymbol elsepart = new NonterminalSymbol("else-part");
	NonterminalSymbol exp = new NonterminalSymbol("exp");
	TerminalSymbol other = new TerminalSymbol("other");
	TerminalSymbol ifSym = new TerminalSymbol("if");
	TerminalSymbol elseSym = new TerminalSymbol("elseSym");
	TerminalSymbol openParen = new TerminalSymbol("(");
	TerminalSymbol closeParen = new TerminalSymbol(")");
	TerminalSymbol zero = new TerminalSymbol("0");
	TerminalSymbol one = new TerminalSymbol("1");

	public ParseTableTest() {
		
		// Test grammar for unit tests:
		// statement -> if-stmt | *other*
		// if-stmt -> *if* *(* exp *)* statement else-part
		// else-part -> *else* statement | empty
		// exp -> *0* | *1*
		this.grammar = new Grammar();
		grammar.addSymbol(statement);
		grammar.addSymbol(ifstmt);
		grammar.addSymbol(elsepart);
		grammar.addSymbol(exp);
		grammar.addSymbol(other);
		grammar.addSymbol(ifSym);
		grammar.addSymbol(elseSym);
		grammar.addSymbol(openParen);
		grammar.addSymbol(closeParen);
		grammar.addSymbol(zero);
		grammar.addSymbol(one);
		grammar.addSymbol(Symbol.EPSILON);
		
		grammar.setStartRule(statement);
		grammar.addRule(statement, new Rule(ifstmt));
		grammar.addRule(statement, new Rule(other));
		grammar.addRule(ifstmt, new Rule(ifSym, openParen, exp, closeParen, statement, elsepart));
		grammar.addRule(elsepart, new Rule(elseSym, statement));
		grammar.addRule(elsepart, new Rule(Symbol.EPSILON));
		grammar.addRule(exp, new Rule(zero));
		grammar.addRule(exp, new Rule(one));
		
		// just calling constructor so that it will then call first() and follow() methods
		new ParseTable(grammar);
	}
	
	@Test
	public void testFirst() {
		
		String message;
		Collection<TerminalSymbol> expected;
		Collection<TerminalSymbol> actual;
		
		// First(if-stmt) = {if}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(ifSym);
		actual = ifstmt.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(statement) = {if, other}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(ifSym);
		expected.add(other);
		actual = statement.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(else-part) = {else, empty}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(elseSym);
		expected.add(Symbol.EPSILON);
		actual = elsepart.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// First(exp) = {0, 1}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(zero);
		expected.add(one);
		actual = exp.getFirst();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
	}
	
	@Test
	public void testFollow() {
		
		String message;
		Collection<TerminalSymbol> expected;
		Collection<TerminalSymbol> actual;

		// Follow(statement) = {$, else}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(Symbol.$);
		expected.add(elseSym);
		actual = statement.getFollow();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// Follow(if-stmt) = {$, else}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(Symbol.$);
		expected.add(elseSym);
		actual = ifstmt.getFollow();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// Follow(else-part) = {$, else}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(Symbol.$);
		expected.add(elseSym);
		actual = elsepart.getFollow();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
		
		// Follow(exp) = {)}
		expected = new ArrayList<TerminalSymbol>();
		expected.add(closeParen);
		actual = exp.getFollow();
		message = "Expected: " + expected + " and got: " + actual;
		assertTrue(message, Helper.equalsIgnoreOrder(expected, actual));
	}
}
