/**
 * 
 */
package com.cs3240.parsergenerator.Domain;


import org.junit.Assert;
import org.junit.Test;

/**
 * @author corey
 *
 */
public class ParseTableTest {
	
	@Test
	public void testFirst() {
		
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
		Symbol openParen = new TerminalSymbol("(");
		g.addSymbol(openParen);
		Symbol closeParen = new TerminalSymbol(")");
		g.addSymbol(closeParen);
		Symbol zero = new TerminalSymbol("0");
		g.addSymbol(zero);
		Symbol one = new TerminalSymbol("1");
		g.addSymbol(one);
		Symbol empty = new TerminalSymbol("e");
		g.addSymbol(empty);
		
		g.setStartRule(statement);
		
		
		
		
	}
	
	@Test
	public void testFollow() {
//		Assert.fail("Implement ME!!!");
	}
}
