package com.cs3240.parsergenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.cs3240.parsergenerator.Domain.Grammar;
import com.cs3240.parsergenerator.Domain.NonterminalSymbol;
import com.cs3240.parsergenerator.Domain.Rule;
import com.cs3240.parsergenerator.Domain.Symbol;
import com.cs3240.parsergenerator.Domain.TerminalSymbol;
import com.cs3240.parsergenerator.exceptions.GrammarFileParseException;

public final class GrammarFileParser {

    public static Grammar parse(final File file) throws IOException, GrammarFileParseException {
        Scanner scan = new Scanner(file);
        Grammar grammar = new Grammar();

        int lineNumber = 1;

        try {
            String line;

            // the first line should start with "%Tokens" followed by a list of terminals
            line = scan.nextLine();
            if (!line.startsWith("%Tokens")) {
                String message = "Missing %Tokens section at line " + lineNumber;
                throw new GrammarFileParseException(message);
            }
            String[] terminalStringList = line.split(" ");
            for (int i = 1; i < terminalStringList.length; i++) {
            	grammar.addSymbol(new TerminalSymbol(terminalStringList[i]));
            }
            lineNumber++;

            // the second line should start with "%Non-terminals" followed by a list of non-terminals
            line = scan.nextLine();
            if (!line.startsWith("%Non-terminals")) {
                String message = "Missing %Non-terminals section at line " + lineNumber;
                throw new GrammarFileParseException(message);
            }
            String[] nonTerminalStringList = line.split(" ");
            for (int i = 1; i < nonTerminalStringList.length; i++) {
            	grammar.addSymbol(new NonterminalSymbol(nonTerminalStringList[i]));
            }
            lineNumber++;

            // the third line should start with "%Start" followed by the start symbol
            line = scan.nextLine();
            if (!line.startsWith("%Start")) {
                String message = "Missing %Start section at line " + lineNumber;
                throw new GrammarFileParseException(message);
            }
            try {
                grammar.setStartSymbol(new NonterminalSymbol(line.split(" ")[1]));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new GrammarFileParseException(lineNumber);
            }
            lineNumber++;

            // the forth line should be only "%Rules"
            line = scan.nextLine();
            if (!line.startsWith("%Rules")) {
                String message = "Missing %Rules section at line " + lineNumber;
                throw new GrammarFileParseException(message);
            }
            lineNumber++;

        } catch (NoSuchElementException e) {
            throw new GrammarFileParseException("Found fewer lines than expected.", e);
        }

        // all following lines should just be rules
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(" ");

            if (line.length < 3) {
                String message = "Too few symbols for a rule at line " + lineNumber;
                throw new GrammarFileParseException(message);
            }

            // first thing on the line is the left-hand side of the rule
            NonterminalSymbol lhs = new NonterminalSymbol(line[0]);

            List<Symbol> rhs = new ArrayList<Symbol>();

            // second item should be a single ":".
            // all remaining items should be the right-hand side of a rule
            for (int i = 2; i < line.length; i++) {
                String current = line[i];

                // a "|" means the end of the current rule
                if (current.equals("|")) {
                    if (rhs.isEmpty()) {
                        throw new GrammarFileParseException("Empty rule at line " + lineNumber);
                    }
                    grammar.addRule(new Rule(lhs, rhs));
                    rhs.clear();

                // an item starting with a "<" is a non-terminal
                } else if (current.startsWith("<")) {
                    rhs.add(new NonterminalSymbol(current));
                } else {
                    rhs.add(new TerminalSymbol(current));
                }
            }
            grammar.addRule(new Rule(lhs, rhs));

            lineNumber++;
        }
        return grammar;
    }
}
