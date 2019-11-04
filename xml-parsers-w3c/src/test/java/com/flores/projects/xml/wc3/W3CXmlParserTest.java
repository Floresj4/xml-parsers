package com.flores.projects.xml.wc3;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flores.projects.xml.w3c.W3CXmlParser;


public class W3CXmlParserTest {
	
	private static int numberOfExecutions = 500;
	private static File samlFile = null;
	
	private static final Logger logger = LoggerFactory.getLogger(W3CXmlParserTest.class);
	
	@BeforeClass
	public static void init() throws Exception {
		samlFile = new File("../src/main/resources/saml.xml");
		if(!samlFile.exists()) {
			throw new FileNotFoundException("saml.xml not found");
		}
		
		PropertyConfigurator.configure("../log4j.properties");
	}

	@Test
	public void testW3CWithElementTraversal() throws Exception {
		long totalTime = 0;
		long minTime = 100;
		long maxTime = 0;
		
		try {
			for(int i = 0; i < numberOfExecutions; i++) {
				/**
				 * execute and get timings.  Intentionally recreate
				 * the jdom implementation each time to capture the overhead
				 * associated with creating the builder and document
				 */
				long start = System.currentTimeMillis();	
				String saml = new W3CXmlParser().getSamlAssertion(samlFile);
				long end = System.currentTimeMillis();
				
				//ensure the token is actually - not null, not empty
				assertNotNull(saml);
				assertNotEquals("", saml.trim());

				//keep track of some timings
				long currentExecution = (end - start);
				totalTime += currentExecution;
				maxTime = currentExecution > maxTime ? currentExecution : maxTime;
				minTime = currentExecution < minTime ? currentExecution : minTime;
			}
		}
		finally {
			logger.info("Average execution in milliseconds: {}", (totalTime / numberOfExecutions));
			logger.info("Max execution in milliseconds: {}", maxTime);
			logger.info("Min execution in milliseconds: {}", minTime);
			logger.info("Total executions: {}", numberOfExecutions);
		}
	}
}