package com.qa.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestUtils {
	public static final long WAIT = 10;

	public HashMap<String,String> parseStringXML(InputStream file) throws Exception{
		
		HashMap<String,String> stringMap=new HashMap<String,String>();
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.parse(file);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		
		NodeList nodeList = doc.getElementsByTagName("string");
		
	
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
			Node node = nodeList.item(itr);  
			
				if (node.getNodeType() == Node.ELEMENT_NODE)   
					{  
						Element eElement = (Element) node;  
						stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
					}  
		}
	return stringMap;
	}
	
	public String getDateTime() {
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-DD-HH-mm-ss");
		Date date=new Date();
		return dateFormat.format(date);
	}
}
