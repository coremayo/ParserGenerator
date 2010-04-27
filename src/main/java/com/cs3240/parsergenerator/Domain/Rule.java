package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Rule implements Comparable<Rule>, Cloneable {

    private NonterminalSymbol lhs;

    private List<Symbol> theRule;

    public Rule(NonterminalSymbol lhs, List<Symbol> rhs) {
        this.lhs = lhs;
        this.theRule = rhs;
    }

    public Rule(NonterminalSymbol lhs, Symbol...rule) {
        this.lhs = lhs;
        this.theRule = new ArrayList<Symbol>(rule.length);
        for (Symbol s : rule) {
            theRule.add(s);
        }
    }

    public void setRule(List<Symbol> rule) {
        this.theRule = rule;
    }

    public List<Symbol> getRule() {
        return theRule;
    }

    public NonterminalSymbol getLhs() {
        return lhs;
    }

    public void setLhs(NonterminalSymbol lhs) {
        this.lhs = lhs;
    }

    public Symbol get(final int i) {
        return theRule.get(i);
    }

    public void remove(final int i) {
        theRule.remove(i);
    }

    public void addRuleToFront(final Rule rule) {
        //ensures ordering correctly, so that if we're adding Bb to this rule, it
        //adds b to beginning first, then adds B to the beginning of that.
        for (int i = rule.getRule().size() - 1; i >= 0; i--) {
            theRule.add(0, rule.get(i));
        }
    }

    public void add(final Symbol s) {
        theRule.add(s);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(lhs + " : ");
        ListIterator<Symbol> it = theRule.listIterator();
        while (it.hasNext()) {
            builder.append(it.next());
            if (it.hasNext()) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Rule ? theRule.equals(((Rule)o).theRule) : false;
    }

    @Override
    public int hashCode() {
        return theRule.hashCode();
    }

    @Override
    public int compareTo(Rule that) {
        if (this.equals(that)) {
            return 0;
        }

        if (this.lhs != null && !this.lhs.equals(that)) {
            return this.lhs.compareTo(that.lhs);
        }

        if (this.theRule.size() != that.theRule.size()) {
            return this.theRule.size() - that.theRule.size();
        }

        // we know at this point that both rules are the same size
        for (int i = 0; i < this.theRule.size(); i++) {
            if (!this.theRule.get(i).equals(that.theRule.get(i))) {
                return this.theRule.get(i).compareTo(that.theRule.get(i));
            }
        }
        return 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Object o = super.clone();
        Rule rule = (Rule) o;

        rule.setLhs((NonterminalSymbol) lhs.clone());
        rule.setRule(new ArrayList<Symbol>(theRule));

        return rule;
    }

    public boolean startsWith(Symbol s) {
        if (theRule.isEmpty()) {
            return false;
        }
        return theRule.get(0).equals(s);
    }
}
