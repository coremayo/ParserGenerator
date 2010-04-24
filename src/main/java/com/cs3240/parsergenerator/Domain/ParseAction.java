package com.cs3240.parsergenerator.Domain;

public class ParseAction {
	private NonterminalSymbol leftHandSide;
	private Rule rule;
	
	public ParseAction(NonterminalSymbol lhs, Rule rule) {
		this.leftHandSide = lhs;
		this.rule = rule;
	}

	public NonterminalSymbol getLeftHandSide() {
		return leftHandSide;
	}

	public void setLeftHandSide(NonterminalSymbol leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

}
