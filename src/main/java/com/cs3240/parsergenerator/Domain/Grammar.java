package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.cs3240.parsergenerator.Domain.Symbol.EPSILON;

/**
 * @author Bobby
 * The master class for the Grammar.  This will keep a Mapping of all the Symbols
 * and their names.  This will also be able to get out all the rules for each
 * non-terminal by keeping a mapping of nonterminal symbols to rules.
 */
public class Grammar {
    private List<TerminalSymbol> terminalSymbols;
    private List<NonterminalSymbol> nonterminalSymbols;
    private Map<NonterminalSymbol, List<Rule>> rulesMap;
    private NonterminalSymbol startSymbol;

    public Grammar() {
        this.terminalSymbols = new ArrayList<TerminalSymbol>();
        this.nonterminalSymbols = new ArrayList<NonterminalSymbol>();
        this.rulesMap = new HashMap<NonterminalSymbol, List<Rule>>();
    }

    /**
     * Adds a symbol to this grammar only if the symbol isn't already in the
     * grammar, i.e. checks for duplicates.
     *
     * @param symbol The symbol to add.
     */
    private void addSymbol(Symbol symbol) {
        if (symbol instanceof TerminalSymbol) {
            if (!terminalSymbols.contains(symbol) && !EPSILON.equals(symbol)) {
                terminalSymbols.add((TerminalSymbol) symbol);
            }
        } else {
            if (!nonterminalSymbols.contains(symbol)) {
                nonterminalSymbols.add((NonterminalSymbol)symbol);
            }

        }
    }

    /**
     * Adds a rule to the rules map. Also, if any of the symbols in the rule
     * are not already in the grammar, they are also added.
     *
     * @param symbol A Nonterminal, since they are only symbols with rules
     * @param rule The rule for this nonterminal
     */
    public void addRule(Rule rule) {

        addSymbol(rule.getLhs());
        for (Symbol s : rule.getRule()) {
            addSymbol(s);
        }

        List<Rule> rules = rulesMap.get(rule.getLhs());
        if (rules == null) {
            rules = new ArrayList<Rule>();
        }
        rules.add(rule);
        rulesMap.put(rule.getLhs(), rules);
    }

    /**
     * This gets the rule with Symbols in it out of the map.
     * @param symbol The nonterminal that we are getting the rule for.
     * @return a List of Symbols representing the rule for this nonterminal.
     */
    public List<Rule> getRules(NonterminalSymbol symbol) {
        List<Rule> rules = rulesMap.get(symbol);

        return rules;
    }

    public void setStartSymbol(NonterminalSymbol startSymbol) {
        addSymbol(startSymbol);
        this.startSymbol = startSymbol;
    }

    /**
     * @return Gets the start symbol for the grammar, not the actual rule.
     */
    public NonterminalSymbol getStartSymbol() {
        return startSymbol;
    }

    /**
     * This is for sake of debugging, returns in format
     *
     * TERMINALS: ...
     * NONTERMINALS: ...
     * START: ...
     * RULES:
     * <>: ...
     * <>: ...
     *
     */
    public String toString() {
        //First, the symbols
        StringBuilder build = new StringBuilder();
        build.append("TERMINAL SYMBOLS (" + terminalSymbols.size() + "): ");
        for (Symbol s : terminalSymbols) {
            build.append(s.getName() + " ");
        }
        build.append("\n");
        build.append("NONTERMINAL SYMBOLS (" + nonterminalSymbols.size() + "): ");
        for (Symbol s : nonterminalSymbols) {
            build.append(s.getName() + " ");
        }
        build.append("\n");
        //Now, the Rules
        Set<NonterminalSymbol> keySetRules = rulesMap.keySet();
        build.append("RULES: \n");
        for (NonterminalSymbol sym : keySetRules) {
            List<Rule> rules = rulesMap.get(sym);
            for (Rule rule : rules) {
                build.append(sym.getName() + " : ");
                for (Symbol s : rule.getRule()) {
                    build.append(s + " ");
                }
                build.append("\n");
            }
        }
        return build.toString();
    }

    /**
     * Goes through and removes general left recursion and immediate left recursion.
     *
     * TODO: See if this works without Left Factoring code.
     */
    public void removeLeftRecursion() {
        //create copy of list since listOfNonterminals will be changing from immediate
        //left recursion.
        List<NonterminalSymbol> tempList =
            new ArrayList<NonterminalSymbol>(nonterminalSymbols);

        //Nasty for loop of death, check firsts, removes general left recursion
        //then goes on to remove any immediate left recursion.
        for (int i = 0; i < tempList.size(); i++) {
            NonterminalSymbol ai = tempList.get(i);
            for (int j = 0; j < i; j++) {
                NonterminalSymbol aj = tempList.get(j);
                List<Rule> rulesForAi = rulesMap.get(ai);
                List<Rule> rulesForAj = rulesMap.get(aj);
                List<Rule> rulesToRemove = new ArrayList<Rule>();
                List<Rule> rulesToAdd = new ArrayList<Rule>();
                for (Rule rule : rulesForAi) {
                    if (rule.get(0).equals(aj)) {
                        rulesToRemove.add(rule);
                        for (Rule ruleJ : rulesForAj) {
                            Rule ruleClone = null;
                            try {
                                ruleClone = (Rule) rule.clone();
                            } catch (CloneNotSupportedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            ruleClone.remove(0);
                            ruleClone.addRuleToFront(ruleJ);
                            rulesToAdd.add(ruleClone);
                        }
                    }
                }
                List<Rule> newRulesForAi = new ArrayList<Rule>(rulesForAi);
                newRulesForAi.removeAll(rulesToRemove);
                newRulesForAi.addAll(rulesToAdd);
                rulesMap.put(ai, newRulesForAi);
            }
            immediateLeftRecursion(ai);
        }
        //Now to do the left factoring, god I hope this works

        boolean isChanging = true;

        // TODO
        /*
         * HERE IT IS
         * WHERE THE INFINITE LOOP HAPPENS
         */
        while(isChanging) {
            isChanging = false;
            for (NonterminalSymbol nonTermSymbol : new ArrayList<NonterminalSymbol>(nonterminalSymbols)) {
                List<Rule> rulesForNonTerm = rulesMap.get(nonTermSymbol);
                Map<Symbol, Integer> alphaCounts = new HashMap<Symbol, Integer>();
                List<Rule> rulesToRemove = new ArrayList<Rule>();
                List<Rule> rulesToAdd = new ArrayList<Rule>();
                Map<NonterminalSymbol, List<Rule>> newRulesForNewSymbols = new HashMap<NonterminalSymbol, List<Rule>>();
                for (Rule rule : rulesForNonTerm) {
                    Symbol first = rule.get(0);
                    if (alphaCounts.get(first) == null) {
                        alphaCounts.put(first, 1);
                        continue;
                    }
                    alphaCounts.put(first, alphaCounts.get(first) + 1);
                }
                Symbol maxAlpha = null;
                int maxCount = 0;
                for (Symbol s : alphaCounts.keySet()) {
                    if (alphaCounts.get(s) > maxCount) {
                        maxAlpha = s;
                        maxCount = alphaCounts.get(s);
                    }
                }
                if (maxCount < 2) {
                    continue;
                } else {
                    isChanging = true;
                }
                for (Rule rule : rulesForNonTerm) {
                    if (rule.get(0).equals(maxAlpha)) {
                        rulesToRemove.add(rule);
                        List<Symbol> newRule = new ArrayList<Symbol>();
                        NonterminalSymbol newSymbol = new NonterminalSymbol(maxAlpha.getName() + "FACTOR");
                        newRule.add(rule.get(0));
                        newRule.add(newSymbol);
                        if (!nonterminalSymbols.contains(newSymbol)) {
                            nonterminalSymbols.add(newSymbol);
                        }
                        if (!rulesToAdd.contains(new Rule(nonTermSymbol, newRule))) {
                            rulesToAdd.add(new Rule(nonTermSymbol, newRule));
                        }
                        Rule ruleClone = null;
                        try {
                            ruleClone = (Rule) rule.clone();
                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ruleClone.remove(0);
                        List<Rule> rulesForNewSymbol = newRulesForNewSymbols.get(newSymbol);
                        if (rulesForNewSymbol == null) {
                            rulesForNewSymbol = new ArrayList<Rule>();
                        }
                        if (ruleClone.getRule().size() > 0) {
                            rulesForNewSymbol.add(ruleClone);
                        } else {
                            rulesForNewSymbol.add(new Rule(nonTermSymbol, EPSILON));
                        }
                        newRulesForNewSymbols.put(newSymbol, rulesForNewSymbol);

                    }

                }
                List<Rule> newRulesForNonterm = new ArrayList<Rule>(rulesForNonTerm);
                newRulesForNonterm.removeAll(rulesToRemove);
                newRulesForNonterm.addAll(rulesToAdd);
                rulesMap.put(nonTermSymbol, newRulesForNonterm);
                for (NonterminalSymbol newSym : newRulesForNewSymbols.keySet()) {
                    List<Rule> newRules = newRulesForNewSymbols.get(newSym);
                    rulesMap.put(newSym, newRules);
                }
            }
        }

    }

    /**
     * Goes through and removes any immediate left recursion from
     * the non terminal rules of ai
     * @param ai The Non-terminal to check on.
     */
    private void immediateLeftRecursion(NonterminalSymbol ai) {
        boolean isNeeded = false; //becomes true is immediate recursion occurs
        List<Rule> rulesForAi = rulesMap.get(ai);
        List<Rule> rulesToAdd = new ArrayList<Rule>();
        List<Rule> rulesToRemove = new ArrayList<Rule>();
        NonterminalSymbol newSymbol = new NonterminalSymbol(ai.getName() + "Prime");
        List<Rule> rulesForNewSymbol = new ArrayList<Rule>();
        /*
         * This goes through each rule and finds if there is any immediate
         * recursion.  If there is, we add that rule to be removed, and we
         * add a rule that removes the first symbol in the rule (should be
         * ai) and adds to the add our new Symbol (ai prime).
         */
        for (Rule rule : rulesForAi){
            Rule ruleClone = null;
            try {
                ruleClone = (Rule) rule.clone();
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (ruleClone.get(0).equals(ai)) {
                rulesToRemove.add(rule);
                ruleClone.remove(0);
                ruleClone.add(newSymbol);
                rulesForNewSymbol.add(ruleClone);
                isNeeded = true;
                continue;
            }
        }
        /*
         * Only runs the actual code to make changes if changes are
         * necessary.
         */
        if (isNeeded) {
            List<Symbol> episilonRule = new ArrayList<Symbol>();
            episilonRule.add(EPSILON);
            Rule epsilon = new Rule(newSymbol, episilonRule);
            rulesForNewSymbol.add(epsilon);
            List<Rule> newRulesForAi = new ArrayList<Rule>(rulesForAi);
            newRulesForAi.removeAll(rulesToRemove);
            newRulesForAi.addAll(rulesToAdd);
            //goes through each rule and adds to end the new symbol.
            for (Rule rule : newRulesForAi) {
                rule.add(newSymbol);
            }
            rulesMap.put(ai, newRulesForAi);
            nonterminalSymbols.add(newSymbol);
            rulesMap.put(newSymbol, rulesForNewSymbol);
        }
    }

    public List<TerminalSymbol> getTerminals() {
        return terminalSymbols;
    }

    /**
     * @return A list of all nonterminal symbols used in the grammar.
     */
    public List<NonterminalSymbol> getNonTerminals() {
        return this.nonterminalSymbols;
    }

    public Symbol getSymbol(String name) {
        for (Symbol s : nonterminalSymbols) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        for (Symbol s : terminalSymbols) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public List<Rule> getAllRules() {
        List<Rule> ret = new ArrayList<Rule>();

        for (NonterminalSymbol sym : this.rulesMap.keySet()) {
            List<Rule> rules = this.rulesMap.get(sym);
            for (Rule rule : rules) {
                rule.setLhs(sym);
                ret.add(rule);
            }
        }
        return ret;
    }

}
