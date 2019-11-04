package com.flores.projects.xml.jdom;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flores.projects.xml.XmlParserImpl;
import com.flores.projects.xml.exceptions.ParserException;

public class JdomXpathXmlParser implements XmlParserImpl, NamespaceContextImpl {

	private static Logger logger = LoggerFactory.getLogger(JdomXpathXmlParser.class);

	private XMLOutputter output;
	
	public JdomXpathXmlParser() {
		output = new XMLOutputter();
	}
	
	@Override
	public String getSamlAssertion(File file) throws ParserException {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(file);
			
			//use the default xpath implementation
			XPathFactory xpathFactory = XPathFactory.instance();
			XPathExpression<Element> assertionPath = xpathFactory.compile(
					"/soap:Envelope/soap:Header/wsse:Security/saml:Assertion", 
					Filters.element(), null, new Namespace[] {
							namespace_saml, namespace_wsse, namespace_soap
			});
			
			//evaluate the expression against the current document
			Element assertion = assertionPath.evaluateFirst(document);
			return output.outputString(assertion);
		}
		catch(Exception e) {
			logger.error("An error occurred parsing saml xml: {}", e.getMessage());
			throw new ParserException(e.getMessage());
		}
	}
}
