package com.cs3240.parsergenerator.Domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.cs3240.parsergenerator.GrammarFileParser;
import com.cs3240.parsergenerator.utils.Driver;

public class SuperTest {

	@Test
	public void testEverything() throws IOException {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-Hmmss");
		Grammar grammar = GrammarFileParser.parse("GrammarSample.txt");
		grammar.removeLeftRecursion();
		ParseTable pt = new ParseTable(grammar);
		Driver.outputTableToFile(pt, "output-" + df.format(new Date()) + ".txt");

		assertTrue(Driver.parse(pt, "LEFTPAR NUMBER ID NUMBER RIGHTPAR"));
		assertFalse(Driver.parse(pt, "NUMBER ID NUMBER RIGHTPAR"));
		assertFalse(Driver.parse(pt, "LEFTPAR NUMBER ID NUMBER"));
		assertFalse(Driver.parse(pt, "LEFTPAR NUMBER NUMBER LEFTPAR NUMBER NUMBER NUMBER RIGHTPAR NUMBER RIGHTPAR LEFTPAR NUMBER RIGHTPAR"));
	}
}
