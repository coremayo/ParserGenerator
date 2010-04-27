package com.cs3240.parsergenerator.exceptions;

public class GrammarFileParseException extends Exception {

    private static final long serialVersionUID = 1L;

    public GrammarFileParseException(int lineNumber) {
        super("Invalid grammar file syntax found on line " + lineNumber + ".");
    }

    public GrammarFileParseException(String message) {
        super(message);
    }

    public GrammarFileParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
