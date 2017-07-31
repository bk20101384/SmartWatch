package com.bkav.home.lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Xml {
	public static Xml loadFromInputStream(InputStream inputStream) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(inputStream);
			Element root = document.getDocumentElement();
			return new Xml(root);
		} catch (ParserConfigurationException e) {
			return null;
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static Xml loadFromFile(File file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
			Element root = document.getDocumentElement();
			return new Xml(root);
		} catch (ParserConfigurationException e) {
			return null;
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			return null;
		}		
	}
	
	protected Xml(Element element) {
		this.element = element;
		this.child = null;
		Xml last = null;
		NodeList nodeList = this.element.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node instanceof Element) {
				if (child == null) {
					child = new Xml((Element)node);
					last = child;
				}
				else {
					last.next = new Xml((Element)node);
					last = last.next;
				}
			}				
		}	
	}
	
	public Xml getChild() {
		return child;
	}
	
	public Xml getChild(String name) {
		Xml xml = child;
		while (xml != null) {
			if (xml.getName().equals(name))
				return xml;
			xml = xml.getNext();
		}
		return null;
	}
	
	public Xml getNext() {
		return next;
	}
	
	public Xml getNext(String name) {
		Xml xml = next;
		while (xml != null) {
			if (xml.getName().equals(name))
				return xml;
			xml = xml.getNext();
		}
		return null;
	}
	
	public boolean hasAttrib(String attrib) {
		return element.hasAttribute(attrib);
	}
	
	public String getAttrib(String attrib) {
		return element.getAttribute(attrib);
	}
	
	public String getName() {
		return element.getNodeName();
	}
	
	public String getData() {
		return element.getNodeValue();
	}
	
	private Element element;
	
	private Xml child;
	private Xml next;
}
