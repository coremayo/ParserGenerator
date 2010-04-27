package com.cs3240.parsergenerator.Domain;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.cs3240.parsergenerator.utils.Helper;
import com.cs3240.parsergenerator.utils.ParseTableGenerator;

/**
 * Tests the first() and follow() methods. The grammar used in this test is
 * ambiguous, and should not be used for anything else.
 *
 * @author Corey
 */
public class FirstFollowTest {

    private Grammar grammar;
    NonterminalSymbol statement = new NonterminalSymbol("<statement>");
    NonterminalSymbol ifstmt = new NonterminalSymbol("<if-stmt>");
    NonterminalSymbol elsepart = new NonterminalSymbol("<else-part>");
    NonterminalSymbol exp = new NonterminalSymbol("<exp>");
    TerminalSymbol other = new TerminalSymbol("other");
    TerminalSymbol ifSym = new TerminalSymbol("if");
    TerminalSymbol elseSym = new TerminalSymbol("elseSym");
    TerminalSymbol openParen = new TerminalSymbol("(");
    TerminalSymbol closeParen = new TerminalSymbol(")");
    TerminalSymbol zero = new TerminalSymbol("0");
    TerminalSymbol one = new TerminalSymbol("1");

    public FirstFollowTest() {

        // Test grammar for unit tests:
        // statement -> if-stmt | *other*
        // if-stmt -> *if* *(* exp *)* statement else-part
        // else-part -> *else* statement | empty
        // exp -> *0* | *1*
        this.grammar = new Grammar();
        grammar.setStartSymbol(statement);
        grammar.addRule(new Rule(statement, ifstmt));
        grammar.addRule(new Rule(statement, other));
        grammar.addRule(new Rule(ifstmt, ifSym, openParen, exp, closeParen, statement, elsepart));
        grammar.addRule(new Rule(elsepart, elseSym, statement));
        grammar.addRule(new Rule(elsepart, Symbol.EPSILON));
        grammar.addRule(new Rule(exp, zero));
        grammar.addRule(new Rule(exp, one));

        ParseTableGenerator.first(grammar);
        ParseTableGenerator.follow(grammar);
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
