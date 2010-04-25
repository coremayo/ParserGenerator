package com.cs3240.parsergenerator.Domain;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.cs3240.parsergenerator.utils.Driver;

public class DriverTest {
	private ParseTable table = null;
	
	public DriverTest() {
		try {
			table = Driver.getTableFromFile("testParseTableFile.txt");
		} catch (FileNotFoundException e) {
			fail("BAD FILE");
		}
	}

	@Test
	public void testParse() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetTableFromFile() {
		
		assertTrue(table != null);
		assertTrue(table.getTerminalSymbols() != null);
		assertTrue(table.getTerminalSymbols().size() == 7);
		assertTrue(table.getNonTerminalSymbols() != null);
		assertTrue(table.getStartState() != null);
		assertTrue(table.getNonTerminalSymbols().size() == 7);
		assertTrue(table.getAllEntries() != null);
		for(ParseTableEntry entry : table.getAllEntries()) {
			System.out.println(entry);
		}
		assertTrue(table.getAllEntries().size() == 18);
	}
	
	@Test
	public void testOutputTableToFile() {
		try {
			Driver.outputTableToFile(table, "parseTableOutputTest.txt");
		} catch (IOException e) {
			fail("ISSUES");
		}
	}

}
