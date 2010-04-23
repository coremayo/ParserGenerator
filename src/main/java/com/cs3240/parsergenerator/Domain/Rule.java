package com.cs3240.parsergenerator.Domain;

import java.util.ArrayList;
import java.util.List;

public class Rule {
	private List<String> theRule;
	
	public Rule(List<String> rule) {
		theRule = rule;
	}
	
	public void setRule(List<String> rule) {
		this.theRule = rule;
	}
	
	public List<String> getRule() {
		return theRule;
	}

}
