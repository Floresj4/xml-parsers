package com.flores.projects.xml.jdom;

import org.jdom2.Namespace;

public interface NamespaceContextImpl {
	static Namespace namespace_soap = Namespace.getNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope");
	static Namespace namespace_wsse = Namespace.getNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
	static Namespace namespace_saml = Namespace.getNamespace("saml", "urn:oasis:names:tc:SAML:1.0:assertion");
}
