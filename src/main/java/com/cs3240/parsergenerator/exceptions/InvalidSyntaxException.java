package com.cs3240.parsergenerator.exceptions;


import com.cs3240.parsergenerator.Domain.Symbol;

public class InvalidSyntaxException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidSyntaxException(Symbol badSymbol, String currentSentence) {
		super("Invalid syntax on " + badSymbol.toString() +"\n"
				+ "Current Sentence: " + currentSentence); 
	}

}
