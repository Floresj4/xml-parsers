package com.flores.projects.xml.jdom;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import com.flores.projects.xml.XmlParserImpl;
import com.flores.projects.xml.exceptions.ParserException;

public class JdomXmlParserImpl implements XmlParserImpl {

	private XMLOutputter output;

	private static Namespace namespace_soap = Namespace.getNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope");
	private static Namespace namespace_wsse = Namespace.getNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
	private static Namespace namespace_saml = Namespace.getNamespace("saml", "urn:oasis:names:tc:SAML:1.0:assertion");
	
	public JdomXmlParserImpl() {
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
