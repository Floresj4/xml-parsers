package com.flores.projects.xml;

import java.io.File;

import com.flores.projects.xml.exceptions.ParserException;

/**
 * @author Jason
 */
public interface XmlParserImpl {
	String getSamlAssertion(File file) throws ParserException;
}
