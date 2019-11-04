package com.flores.projects.xml.w3c;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flores.projects.xml.XmlParserImpl;
import com.flores.projects.xml.exceptions.ParserException;

/**
 * default provided by the jdk
 * @author Jason
 */
public class W3CXmlParser implements XmlParserImpl {

	private static final Logger logger = LoggerFactory.getLogger(W3CXmlParser.class);

	@Override
	public String getSamlAssertion(File file) throws ParserException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			
			Element root = document.getDocumentElement();
			
			//not the most elegant, but works
			Node header = fromChildren("soap:Header", root.getChildNodes());
			Node security = fromChildren("wsse:Security", header.getChildNodes());
			if(security != null) {
				Node assertion = fromChildren("saml:Assertion", security.getChildNodes());
				if(assertion != null) {
					StringWriter writer = new StringWriter();
					Transformer transformer = TransformerFactory.newInstance().newTransformer();
					transformer.transform(new DOMSource(assertion), new StreamResult(writer));
					return writer.toString();
				}	
			}

			return null;
		}
		catch(Exception e) {
			logger.error("An error occurred parsing XML document: {}", e);
			throw new ParserException(e.getMessage());
		}
	}
	
	private static Node fromChildren(String name, NodeList list) {
		for(int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if(n.getNodeName().equalsIgnoreCase(name)) {
				return n;
			}
		}
		
		return null;
	}
}
