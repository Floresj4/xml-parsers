package com.flores.projects.xml.jdom;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

public class JdomXmlParserImplTest  {
	
	private static File samlFile = null;
	private JdomXmlParserImpl jdomParser = new JdomXmlParserImpl();
	
	@BeforeClass
	public static void init() throws Exception {
		samlFile = new File("../src/main/resources/saml.xml");
		if(!samlFile.exists()) {
			throw new FileNotFoundException("saml.xml not found");
		}
	}

	@Test
	public void testSaxBuilderWithElementTraversal() throws Exception {
		try {
			long start = System.currentTimeMillis();	
			String saml = jdomParser.getSamlAssertion(samlFile);
			long end = System.currentTimeMillis();
			
			assertNotNull(saml);
			System.out.println(saml);
			System.out.println(format("duration in milliseconds: %d", (end - start)));
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			
		}
		
	}
}
