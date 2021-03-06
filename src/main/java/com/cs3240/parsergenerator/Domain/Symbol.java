package com.cs3240.parsergenerator.Domain;

import java.util.Set;

/**
 * @author Bobby
 * This will be the base for the terminal and non-terminal objects.  Since
 * both of these will be used in the grammar together, I figure that they should
 * both inherit this class.  Also, they both have names, might as well add that method
 * here.
 */
public abstract class Symbol implements Comparable<Symbol>, Cloneable {

    /** Represents epsilon, or the empty symbol. */
    public static final TerminalSymbol EPSILON = new TerminalSymbol(".");

    /** $, the special bottom of the stack terminal symbol. */
    public static final TerminalSymbol $ = new TerminalSymbol("$");

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<TerminalSymbol> getFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Symbol) {
            return name.equals(((Symbol) obj).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Symbol s) {
        return name.compareTo(s.name);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        Symbol s = (Symbol) o;
        s.name = new String(name);
        return s;
    }
}
