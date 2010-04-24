package com.cs3240.parsergenerator.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Stack;

import com.cs3240.parsergenerator.Domain.NonterminalSymbol;
import com.cs3240.parsergenerator.Domain.ParseAction;
import com.cs3240.parsergenerator.Domain.ParseTable;
import com.cs3240.parsergenerator.Domain.ParseTableEntry;
import com.cs3240.parsergenerator.Domain.Rule;
import com.cs3240.parsergenerator.Domain.Symbol;
import com.cs3240.parsergenerator.Domain.TerminalSymbol;

public class Driver {

	public static boolean parse(ParseTable table, String input) {
		Stack<Symbol> parsingStack = new Stack<Symbol>();
		LinkedList<TerminalSymbol> inputQueue = new LinkedList<TerminalSymbol>();
		//add input to queue
		for (String s : input.split(" ")) {
			inputQueue.add(new TerminalSymbol(s));
		}
		inputQueue.add(Symbol.$);
		
		//add the start state and $ to parsing stack.
		parsingStack.push(Symbol.$);
		parsingStack.push(table.getStartState());
		
		while(!parsingStack.peek().equals(Symbol.$) && !inputQueue.peekFirst().equals(Symbol.$)) {
			//MATCH
			if (parsingStack.peek().equals(inputQueue.peekFirst())) {
				parsingStack.pop();
				inputQueue.remove();
			
			//GENERATE	
			} else if (parsingStack.peek() instanceof NonterminalSymbol
					&& table.getTableEntry(
							(NonterminalSymbol) parsingStack.peek(), inputQueue.peek())
							.getNonTerminal().equals(parsingStack.peek())) {
				parsingStack.pop();
				for (Symbol s :  table.getTableEntry(
							(NonterminalSymbol) parsingStack.peek(), inputQueue.peek())
							.getAction().getRule().getRule()) {
					parsingStack.push(s);
				}
			} else {
				return false;
			}
		}
		if (parsingStack.peek().equals(Symbol.$) 
				&& inputQueue.peek().equals(Symbol.$)) {
			return true;
		}
		return false;
	}
	
	public static void outputTableToFile(ParseTable table) throws IOException {
		List<ParseTableEntry> entries = table.getAllEntries();
		List<TerminalSymbol> termSymbols = table.getTerminalSymbols();
		List<NonterminalSymbol> nonTermSymbols = table.getNonTerminalSymbols();
		StringBuilder builder = new StringBuilder();
		builder.append("%Terminals ");
		ListIterator<TerminalSymbol> termIterator = termSymbols.listIterator();
		while(termIterator.hasNext()) {
			builder.append(termIterator.next());
			if (termIterator.hasNext()) {
				builder.append(", ");
			}
		}
		builder.append("\n");
		builder.append("%NonTerminals ");
		ListIterator<NonterminalSymbol> nonTermIterator = nonTermSymbols.listIterator();
		while(nonTermIterator.hasNext()) {
			builder.append(nonTermIterator.next());
			if (nonTermIterator.hasNext()) {
				builder.append(", ");
			}
		}
		builder.append("\n");
		builder.append("%Table Entries");
		builder.append("\n");
		for (ParseTableEntry entry : entries) {
			builder.append(entry.getNonTerminal());
			builder.append(", ");
			builder.append(entry.getTerminal());
			builder.append(", ");
			builder.append(entry.getAction().getLeftHandSide());
			builder.append(" : ");
			builder.append(entry.getAction().getRule().toString());
			builder.append("\n");
		}
		FileWriter writer = new FileWriter(new File("parseTable.txt"));
		writer.write(builder.toString());
	}
	
	public static ParseTable getTableFromFile(String filename) throws FileNotFoundException {
		Scanner tableScanner = new Scanner(new File(filename));
		tableScanner.useDelimiter("\n");
		ParseTable table = new ParseTable();
		List<ParseTableEntry> entries = new ArrayList<ParseTableEntry>();
		while (tableScanner.hasNext()) {
			String[] lineSplit = tableScanner.next().split(",");
			if (lineSplit[0].trim().equals("%Terminals")) {
				List<TerminalSymbol> symbols = new ArrayList<TerminalSymbol>();
				for (int i = 1; i < lineSplit.length; i++) {
					symbols.add(new TerminalSymbol(lineSplit[i].trim()));
				}
				table.setTerminalSymbols(symbols);
			} else if (lineSplit[0].trim().equals("%Terminals")) {
				List<NonterminalSymbol> symbols = new ArrayList<NonterminalSymbol>();
				for (int i = 1; i < lineSplit.length; i++) {
					symbols.add(new NonterminalSymbol(lineSplit[i].trim()));
				}
				table.setNonterminalSymbols(symbols);
			} else if (lineSplit[0].trim().equals("%Table Entries")) {
				continue;
			} else {
				ParseTableEntry entry = new ParseTableEntry();
				entry.setNonTerminal(new NonterminalSymbol(lineSplit[0]));
				entry.setTerminal(new TerminalSymbol(lineSplit[1]));
				ParseAction action = new ParseAction();
				String[] actionString = lineSplit[2].split(" : ");
				action.setLeftHandSide(new NonterminalSymbol(actionString[0]));
				List<Symbol> ruleSymbols = new ArrayList<Symbol>();
				for (String symbolName : actionString[1].split(" ")) {
					for (TerminalSymbol sym : table.getTerminalSymbols()) {
						if (sym.getName().equals(symbolName)) {
							ruleSymbols.add(sym);
							break;
						}
					}
					for (NonterminalSymbol sym : table.getNonTerminalSymbols()) {
						if (sym.getName().equals(symbolName)) {
							ruleSymbols.add(sym);
							break;
						}
					}
				}
				action.setRule(new Rule(ruleSymbols));
				entry.setAction(action);
				entries.add(entry);
			}
		}
		return table;
	}
}
