package com.cs3240.parsergenerator.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import com.cs3240.parsergenerator.exceptions.InvalidSyntaxException;

public class Driver {

	public static boolean parse(ParseTable table, String input) throws InvalidSyntaxException {
		Stack<Symbol> parsingStack = new Stack<Symbol>();
		LinkedList<TerminalSymbol> inputQueue = new LinkedList<TerminalSymbol>();
		StringBuilder currentSentence = new StringBuilder();
		//add input to queue
		for (String s : input.split(" ")) {
			inputQueue.add(new TerminalSymbol(s));
		}
		inputQueue.add(Symbol.$);
		
		//add the start state and $ to parsing stack.
		parsingStack.push(Symbol.$);
		parsingStack.push(table.getStartState());
		
		while(!parsingStack.peek().equals(Symbol.$)) {
			//MATCH
			if (parsingStack.peek().equals(Symbol.EPSILON)) {
				parsingStack.pop();
				continue;
			}else if (parsingStack.peek().equals(inputQueue.peekFirst())) {
				parsingStack.pop();
				currentSentence.append(inputQueue.remove());
				currentSentence.append(" ");
			
			//GENERATE	
			} else if (parsingStack.peek() instanceof NonterminalSymbol
					&& table.getTableEntry(
							(NonterminalSymbol) parsingStack.peek(), inputQueue.peek()) != null
							&& table.getTableEntry(
							(NonterminalSymbol) parsingStack.peek(), inputQueue.peek())
							.getNonTerminal().equals(parsingStack.peek())) {
				NonterminalSymbol nonT = (NonterminalSymbol)parsingStack.pop();
				for (int i = table.getTableEntry(
							nonT, inputQueue.peek())
							.getAction().getRule().getRule().size() - 1; i >= 0; i --) {
					parsingStack.push(table.getTableEntry(
							nonT, inputQueue.peek())
							.getAction().getRule().getRule().get(i));
				}
			} else {
				throw new InvalidSyntaxException(inputQueue.peek(), currentSentence.toString());
			}
		}
		if (parsingStack.peek().equals(Symbol.$) 
				&& inputQueue.peek().equals(Symbol.$)) {
			return true;
		}
		throw new InvalidSyntaxException(inputQueue.peek(), currentSentence.toString());
	}
	
	public static void outputTableToFile(ParseTable table, String filename) throws IOException {
		List<ParseTableEntry> entries = table.getAllEntries();
		List<TerminalSymbol> termSymbols = table.getTerminalSymbols();
		List<NonterminalSymbol> nonTermSymbols = table.getNonTerminalSymbols();
		StringBuilder builder = new StringBuilder();
		builder.append("%Terminals");
		builder.append("\n");
		ListIterator<TerminalSymbol> termIterator = termSymbols.listIterator();
		while(termIterator.hasNext()) {
			builder.append(termIterator.next());
			if (termIterator.hasNext()) {
				builder.append(", ");
			}
		}
		builder.append("\n");
		builder.append("%NonTerminals");
		builder.append("\n");
		ListIterator<NonterminalSymbol> nonTermIterator = nonTermSymbols.listIterator();
		while(nonTermIterator.hasNext()) {
			builder.append(nonTermIterator.next());
			if (nonTermIterator.hasNext()) {
				builder.append(", ");
			}
		}
		builder.append("\n");
		builder.append("%Start State");
		builder.append("\n");
		builder.append(table.getStartState());
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
//		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
//		writer.write(builder.toString());
		try {

		      File file= new File(filename);
		      FileWriter outFile = new FileWriter(file);
		      PrintWriter out = new PrintWriter(outFile);
		      
		      out.print(builder.toString());
		      out.close();

		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
	}
	
	public static ParseTable getTableFromFile(String filename) throws FileNotFoundException {
		Scanner tableScanner = new Scanner(new File(filename));
		tableScanner.useDelimiter("\n");
		ParseTable table = new ParseTable();
		List<ParseTableEntry> entries = new ArrayList<ParseTableEntry>();
		boolean isTerminals = false;
		boolean isNonterminals = false;
		boolean isEntries = false;
		boolean isStart = false;
		while (tableScanner.hasNext()) {
			String[] lineSplit = tableScanner.next().split(",");
			if (lineSplit[0].trim().equals("%Terminals")) {
				isTerminals = true;
				isNonterminals = false;
				isEntries = false;
				isStart = false;
				continue;
			} else if (lineSplit[0].trim().equals("%NonTerminals")) {
				isTerminals = false;
				isNonterminals = true;
				isEntries = false;
				isStart = false;
				continue;
			} else if (lineSplit[0].trim().equals("%Table Entries")) {
				isTerminals = false;
				isNonterminals = false;
				isEntries = true;
				isStart = false;
				continue;
			} else if (lineSplit[0].trim().equals("%Start State")) {
				isStart = true;
				isNonterminals = false;
				isTerminals = false;
				isEntries = false;
				continue;
			} else {
				if (isTerminals) {
					List<TerminalSymbol> symbols = new ArrayList<TerminalSymbol>();
					for (int i = 0; i < lineSplit.length; i++) {
						symbols.add(new TerminalSymbol(lineSplit[i].trim()));
					}
					table.setTerminalSymbols(symbols);
				} else if (isNonterminals) {
					List<NonterminalSymbol> symbols = new ArrayList<NonterminalSymbol>();
					for (int i = 0; i < lineSplit.length; i++) {
						symbols.add(new NonterminalSymbol(lineSplit[i].trim()));
					}
					table.setNonterminalSymbols(symbols);
				} else if (isStart){
					for (NonterminalSymbol sym : table.getNonTerminalSymbols()) {
						if (sym.getName().equals(lineSplit[0].trim())) {
							table.setStartSymbol(sym);
						}
					}
				} else if (isEntries) {
					ParseTableEntry entry = new ParseTableEntry();
					entry.setNonTerminal(new NonterminalSymbol(lineSplit[0].trim()));
					entry.setTerminal(new TerminalSymbol(lineSplit[1].trim()));
					ParseAction action = new ParseAction();
					String[] actionString = lineSplit[2].split(":");
					action.setLeftHandSide(new NonterminalSymbol(actionString[0].trim()));
					List<Symbol> ruleSymbols = new ArrayList<Symbol>();
					for (String symbolName : actionString[1].split(" ")) {
						for (TerminalSymbol sym : table.getTerminalSymbols()) {
							if (sym.getName().equals(symbolName.trim())) {
								ruleSymbols.add(sym);
								break;
							}
						}
						for (NonterminalSymbol sym : table.getNonTerminalSymbols()) {
							if (sym.getName().equals(symbolName.trim())) {
								ruleSymbols.add(sym);
								break;
							}
						}
						if (symbolName.trim().equals(Symbol.EPSILON.getName())) {
							ruleSymbols.add(Symbol.EPSILON);
						}
					}
					action.setRule(new Rule(ruleSymbols));
					entry.setAction(action);
					entries.add(entry);
				}
			}
		}
		table.setTable(entries);
		return table;
	}
}
