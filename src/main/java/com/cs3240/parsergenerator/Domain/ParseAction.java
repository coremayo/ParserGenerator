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
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ParseAction) {
			ParseAction that = (ParseAction) o;
			return this.leftHandSide.equals(that.leftHandSide)
				&& this.rule.equals(that.rule);
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(leftHandSide);
		builder.append(" : ");
		builder.append(rule);
		return builder.toString();
	}
}
