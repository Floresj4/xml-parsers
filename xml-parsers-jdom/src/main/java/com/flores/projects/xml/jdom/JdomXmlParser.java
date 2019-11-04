package com.flores.projects.xml.jdom;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import com.flores.projects.xml.XmlParserImpl;
import com.flores.projects.xml.exceptions.ParserException;

public class JdomXmlParser implements XmlParserImpl, NamespaceContextImpl {

	private XMLOutputter output;
	
	public JdomXmlParser() {
		output = new XMLOutputter();
	}
	
	@Override
	public String getSamlAssertion(File file) throws ParserException {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(file);

			Element root = document.getRootElement();
			Element header = root.getChild("Header", namespace_soap);
			Element security = header.getChild("Security", namespace_wsse);
			Element assertion = security.getChild("Assertion", namespace_saml);
			return output.outputString(assertion);
		}
		catch(Exception e) {
			throw new ParserException(e.getMessage());
		}
	}
}
