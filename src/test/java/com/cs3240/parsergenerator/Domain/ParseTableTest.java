/**
 * 
 */
package com.cs3240.parsergenerator.Domain;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cs3240.parsergenerator.utils.Helper;

/**
 * @author corey
 *
 */
public class ParseTableTest {
	
	@Test
	public void testFirst() {
		
		// Test grammar for unit tests:
		// statement -> if-stmt | *other*
		// if-stmt -> *if* *(* exp *)* statement else-part
		// else-part -> *else* statement | empty
		// exp -> *0* | *1*
		Grammar g = new Grammar();
		NonterminalSymbol statement = new NonterminalSymbol("statement");
		g.addSymbol(statement);
		NonterminalSymbol ifstmt = new NonterminalSymbol("if-stmt");
		g.addSymbol(ifstmt);
		NonterminalSymbol elsepart = new NonterminalSymbol("else-part");
		g.addSymbol(elsepart);
		NonterminalSymbol exp = new NonterminalSymbol("exp");
		g.addSymbol(exp);
		Symbol other = new TerminalSymbol("other");
		g.addSymbol(other);
		Symbol ifSym = new TerminalSymbol("if");
		g.addSymbol(ifSym);
		Symbol elseSym = new TerminalSymbol("elseSym");
		g.addSymbol(elseSym);
		Symbol openParen = new TerminalSymbol("(");
		g.addSymbol(openParen);
		Symbol closeParen = new TerminalSymbol(")");
		g.addSymbol(closeParen);
		Symbol zero = new TerminalSymbol("0");
		g.addSymbol(zero);
		Symbol one = new TerminalSymbol("1");
		g.addSymbol(one);
		g.addSymbol(Symbol.EPSILON);
		
		g.setStartRule(statement);
		g.addRule(statement, new Rule(ifstmt));
		g.addRule(statement, new Rule(other));
		g.addRule(ifstmt, new Rule(ifSym, openParen, exp, closeParen, statement, elsepart));
		g.addRule(elsepart, new Rule(elseSym, statement));
		g.addRule(elsepart, new Rule(Symbol.EPSILON));
		g.addRule(exp, new Rule(zero));
		g.addRule(exp, new Rule(one));
		
		
		// just calling constructor so that it will then call first() method
		new ParseTable(g);
		
		// First(statement) = {if, other}
		// First(if-stmt) = {if}
		// First(else-part) = {else, empty}
		// First(exp) = {0, 1}
		
		List<TerminalSymbol> expected = new ArrayList<TerminalSymbol>();
		expected.add((TerminalSymbol) ifSym);
		List<TerminalSymbol> actual = ifstmt.getFirst();
		assertTrue(Helper.equalsIgnoreOrder(expected, actual));
		
		expected = new ArrayList<TerminalSymbol>();
		expected.add((TerminalSymbol) ifSym);
		expected.add((TerminalSymbol) other);
		actual = statement.getFirst();
		assertTrue(Helper.equalsIgnoreOrder(expected, actual));
		
		expected = new ArrayList<TerminalSymbol>();
		expected.add((TerminalSymbol) elseSym);
		expected.add((TerminalSymbol) Symbol.EPSILON);
		actual = elsepart.getFirst();
		assertTrue(Helper.equalsIgnoreOrder(expected, actual));
		
		expected = new ArrayList<TerminalSymbol>();
		expected.add((TerminalSymbol) zero);
		expected.add((TerminalSymbol) one);
		actual = exp.getFirst();
		assertTrue(Helper.equalsIgnoreOrder(expected, actual));
		
	}
	
	@Test
	public void testFollow() {
//		Assert.fail("Implement ME!!!");
	}
}
