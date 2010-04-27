package com.cs3240.parsergenerator.Domain;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Bobby
 * The Nonterminal Symbol has a name, but no value.
 * It is specific because of how grammar works.
 */
public class NonterminalSymbol extends Symbol {

    private Set<TerminalSymbol> first;

    private Set<TerminalSymbol> follow;

    public NonterminalSymbol(final String name) {
        this.setName(name);
    }

    @Override
    public Set<TerminalSymbol> getFirst() {
        return first;
    }

    public void setFirst(Set<TerminalSymbol> first) {
        this.first = first;
    }

    public Set<TerminalSymbol> getFollow() {
        return follow;
    }

    public void setFollow(Set<TerminalSymbol> follow) {
        this.follow = follow;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        NonterminalSymbol symbol = (NonterminalSymbol) o;
        if (first != null) {
            symbol.first = new TreeSet<TerminalSymbol>(first);
        }
        if (follow != null) {
            symbol.follow = new TreeSet<TerminalSymbol>(follow);
        }
        return symbol;
    }
}
