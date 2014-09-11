package com.test.drools.liveCycle;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsParserException;

public class TestDrools {

	private static final Logger logger = Logger.getLogger(TestDrools.class);
	
	public static String helloWorld(String message) {
		
		logger.debug("calling helloworld");
		WorkingMemory workingMemory = null;
		DroolsUtil drools = new DroolsUtil();	
		try {
			workingMemory = drools.initialiseDrools("HelloWorldRules.drl");			
		} catch (DroolsParserException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		HelloWorld helloWorld = new HelloWorld(message);
		workingMemory.insert(helloWorld);
		workingMemory.fireAllRules();
		logger.debug(helloWorld.getMessage());
		return helloWorld.getMessage();
	}
	
	public static void main(String args[]){
		TestDrools.helloWorld("Have a nice day!");
	}
// Test
}
