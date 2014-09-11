package com.test.drools.liveCycle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.rule.Package;
import org.drools.spi.KnowledgeHelper;

public class DroolsUtil {
	
	private static final Logger logger = Logger.getLogger(DroolsUtil.class);
	
	public WorkingMemory initialiseDrools(String ruleFile) throws IOException,
		DroolsParserException {
		
		PackageBuilder packageBuilder = readRuleFiles(ruleFile);
		RuleBase ruleBase = addRulesToWorkingMemory(packageBuilder);
		return initializeWorkingMemory(ruleBase);		
	}

	private PackageBuilder readRuleFiles(String ruleFile) throws DroolsParserException,
		IOException {
		
		PackageBuilder packageBuilder = new PackageBuilder();
		Reader reader = getRuleFileAsReader(ruleFile);
		packageBuilder.addPackageFromDrl(reader);				
		assertNoRuleErrors(packageBuilder);		
		return packageBuilder;
	}
	
	private Reader getRuleFileAsReader(String ruleFile) {
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);
	
		return new InputStreamReader(resourceAsStream);
	}
	
	private RuleBase addRulesToWorkingMemory(PackageBuilder packageBuilder) {
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		Package rulesPackage = packageBuilder.getPackage();
		ruleBase.addPackage(rulesPackage);
		
		return ruleBase;
	}
	
	private void assertNoRuleErrors(PackageBuilder packageBuilder) {
		
		PackageBuilderErrors errors = packageBuilder.getErrors();	
		if (errors.getErrors().length > 0) {
			StringBuilder errorMessages = new StringBuilder();
			errorMessages.append("Found errors in package builder\n");
			for (int i = 0; i < errors.getErrors().length; i++) {
				DroolsError errorMessage = errors.getErrors()[i];
				errorMessages.append(errorMessage);
				errorMessages.append("\n");
			}
			errorMessages.append("Could not parse knowledge");		
			throw new IllegalArgumentException(errorMessages.toString());
		}
	}
	
	public WorkingMemory initializeWorkingMemory(RuleBase ruleBase) {
		
		WorkingMemory workingMemory = ruleBase.newStatefulSession();		
		return workingMemory;
	}
	
	/**
	 * Log a debug message from a rule, using the rule's package and name as the Log4J
	 * category.
	 */
	public static void log(final KnowledgeHelper drools, final String message,
		final Object... parameters) {

		final String category = drools.getRule().getPackageName() + "." 
			+ drools.getRule().getName();
		final String formattedMessage = String.format(message, parameters);
		Logger.getLogger(category).debug(formattedMessage);
	}
	

}
