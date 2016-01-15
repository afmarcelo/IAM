/**
 * 
 */
package fr.mfa.aim.configuration;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.mfa.aim.datamodel.Identity;

/**
 * @author marcelo
 * This class has all the configuration required for the application.
 */
public class Configuration {

	private String ServerIP;
	private int ServerPort;
	private String XmlFilePath;
	private String WorkingPath;
	private String ConfigFile="files/config.xml";
	private Document doc;
	
	public Configuration(){
		readConfig();
	}
	
	/**
	 * @return the serverIP
	 */
	public String getServerIP() {
		return ServerIP;
	}
	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return ServerPort;
	}
	/**
	 * @return the xmlFilePath
	 */
	public String getXmlFilePath() {
		return XmlFilePath;
	}
	/**
	 * @return the workingPath
	 */
	public String getWorkingPath() {
		return WorkingPath;
	}
	/**
	 * @param serverIP the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		ServerIP = serverIP;
	}
	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		ServerPort = serverPort;
	}
	/**
	 * @param xmlFilePath the xmlFilePath to set
	 */
	public void setXmlFilePath(String xmlFilePath) {
		XmlFilePath = xmlFilePath;
	}
	/**
	 * @param workingPath the workingPath to set
	 */
	public void setWorkingPath(String workingPath) {
		WorkingPath = workingPath;
	}
	
	private void readConfig(){
		
		try {
			// Document Builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// DOM Document builder, to get an empty document or parse it from
			// an xml source
			DocumentBuilder db = dbf.newDocumentBuilder();
			this.doc = db.parse(ConfigFile);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		NodeList nodes = this.doc.getElementsByTagName("configuration");
		int nodesSize = nodes.getLength();
		Node node = nodes.item(0);
		// cast the node into an Element, as we are sure it is an
		// instance of Element
		Element configuration = (Element) node;

		// get the properties for the encountered identity
		NodeList properties = configuration.getElementsByTagName("property");
		int length = properties.getLength();

		// for every found in configuration
		for (int i = 0; i < length; i++) {
			Node property = properties.item(i);
			// test if the found node is really an Element
			// in the DOM implementation a Node can represent an Element, an
			// Attribute or a TextContent
			// so we have to be sure that the found node is of type
			// "Element" using the instance of operator
			if (property instanceof Element) {
				Element currentConfiguration = (Element) property;
				
				String txtContent = currentConfiguration.getTextContent();
				switch(currentConfiguration.getAttribute("name")){
				case "serverip":
					setServerIP(txtContent);
					break;
					
				case "serverport":
					setServerPort(Integer.parseInt(txtContent.toString()));
					break;
					
				case "xmlfilepath":
					setXmlFilePath(txtContent);
					break;
					
				case "workingpath":
					setWorkingPath(txtContent);
					break;
					
				}
					
			}
		}	
	}
}
