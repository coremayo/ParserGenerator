package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;

import com.cs3240.parsergenerator.utils.Helper;

public class ParseTable {

    /** Maps the parse table's DFA state to its entry containing inputs and goto's. */
    private List<ParseTableEntry> table;

    public void setTable(List<ParseTableEntry> table) {
        this.table = table;
    }

    private NonterminalSymbol startSymbol;
    public void setStartSymbol(NonterminalSymbol startSymbol) {
        this.startSymbol = startSymbol;
    }

    private List<NonterminalSymbol> nonTerminalSymbols;
    private List<TerminalSymbol> terminalSymbols;

    public List<NonterminalSymbol> getNonTerminalSymbols() {
        return nonTerminalSymbols;
    }

    public List<TerminalSymbol> getTerminalSymbols() {
        return terminalSymbols;
    }

    public ParseTable(Grammar grammar) {
        this.table = new ArrayList<ParseTableEntry>();
        this.startSymbol = grammar.getStartSymbol();
        this.nonTerminalSymbols = grammar.getNonTerminals();
        this.terminalSymbols = grammar.getTerminals();

    }

    public ParseTable() {
        this.startSymbol = null;
        this.nonTerminalSymbols = null;
        this.terminalSymbols = null;
        this.table = new ArrayList<ParseTableEntry>();
    }




    public void add(NonterminalSymbol non, TerminalSymbol term, Symbol... rule) {
        Rule parseRule = new Rule(non, rule);
        ParseAction act = new ParseAction(non, parseRule);
        ParseTableEntry entry = new ParseTableEntry(non, term, act);
        add(entry);
    }



    public Symbol getStartState() {
        return this.startSymbol;
    }

    public ParseTableEntry getTableEntry(NonterminalSymbol peek, TerminalSymbol peek2) {
        for (ParseTableEntry entry : table) {
            if (entry.getNonTerminal().equals(peek)
                    && entry.getTerminal().equals(peek2)) {
                return entry;
            }
        }
        return null;
    }

    public List<ParseTableEntry> getAllEntries() {
        return table;
    }

    public void setTerminalSymbols(List<TerminalSymbol> symbols) {
        this.terminalSymbols = symbols;

    }

    public void setNonterminalSymbols(List<NonterminalSymbol> symbols) {
        this.nonTerminalSymbols = symbols;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParseTable) {
            ParseTable that = (ParseTable) obj;
            return Helper.equalsIgnoreOrder(this.table, that.table);
        }
        return false;
    }

    public String diff(ParseTable that) {

        StringBuffer ret = new StringBuffer();

        if (this.table.size() != that.table.size()) {
            ret.append("This table has "
                    + this.table.size()
                    +" entries and that table has "
                    + that.table.size() +
                    " entries.\n");
        }

        for (ParseTableEntry entry : this.table) {
            if (!that.table.contains(entry)) {
                ret.append("Entry: " + entry + " is in this but not that.\n");
            }
        }
        for (ParseTableEntry entry : that.table) {
            if (!this.table.contains(entry)) {
                ret.append("Entry: " + entry + " is in that but not this.\n");
            }
        }

        return ret.toString();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (ParseTableEntry entry : table) {
            sb.append(entry + "\n");
        }
        return sb.toString();
    }

    public void add(ParseTableEntry parseTableEntry) {
        table.add(parseTableEntry);

    }
}
