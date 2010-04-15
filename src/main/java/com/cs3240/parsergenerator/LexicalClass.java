package com.cs3240.parsergenerator;

public enum LexicalClass {
	BEGIN("begin", "BEGIN"), 
	END("end", "END"), 
	PRINT("print", "PRINT"), 
	READ("read", "READ"), 
	SEMICOLON(";", "SEMICOLON"), 
	COMMA(",", "COMMA"), 
	ID("([a-zA-Z][a-zA-Z0-9_]{0,9}|_[a-zA-Z0-9][a-zA-Z0-9_]{0,8})", "ID"), 
	INTNUM("[1-9][0-9]*", "INTNUM"), 
	ASSIGN(":=", "ASSIGN"), 
	LEFTPAR("\\(", "LEFTPAR"), 
	RIGHTPAR("\\)", "RIGHTPAR"), 
	PLUS("\\+", "PLUS"), 
	MINUS("-", "MINUS"), 
	MULTIPLY("\\*", "MULTIPLY"), 
	MODULO("%", "MODULO");
	
	private String regex;
	
	private String name;
	
	private LexicalClass(String regex, String name) {
		this.regex = regex;
		this.name = name;
	}
	
	public static LexicalClass parseToken(String s) {
		if (s.matches(BEGIN.regex)) {
			return BEGIN;
		} else if (s.matches(END.regex)) {
			return END;
		} else if (s.matches(PRINT.regex)) {
			return PRINT;
		} else if (s.matches(READ.regex)) {
			return READ;
		}
		for (LexicalClass value : values()) {
			if (s.matches(value.regex)) {
				return value;
			}
		}
		return null;
	}
	
	public String toString() {
		return name;
	}
}
