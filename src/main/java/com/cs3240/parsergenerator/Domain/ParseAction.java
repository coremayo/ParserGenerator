package com.cs3240.parsergenerator.Domain;

public class ParseAction {
	private NonterminalSymbol leftHandSide;
	private Rule rule;
	
	public ParseAction(NonterminalSymbol lhs, Rule rule) {
		this.leftHandSide = lhs;
		this.rule = rule;
	}

	public ParseAction() {
		this(null, null);
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

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(leftHandSide);
		builder.append(" : ");
		builder.append(rule);
		return builder.toString();
	}
}
