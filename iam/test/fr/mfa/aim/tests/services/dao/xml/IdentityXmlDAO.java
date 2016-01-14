/**
 * 
 */
package fr.mfa.aim.tests.services.dao.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;

import fr.mfa.aim.datamodel.Identity;
import fr.mfa.iam.services.dao.IdentityDAO;
import fr.mfa.iam.tests.services.match.Matcher;
import fr.mfa.iam.tests.services.match.impl.StartsWithIdentityMatchStrategy;

/**
 * This class implements all the required method to work with a XML file. 
 * 
 */
public class IdentityXmlDAO implements IdentityDAO {

	// Configure the matcher behavior for the search method.
	Matcher<Identity> matcher = new StartsWithIdentityMatchStrategy();

	private Document doc;
	private String filepath ="files/identities.xml";
	private String tmpfilepath="files/tmp.xml";
	
	public IdentityXmlDAO() {
		try {
			// Intermediate instance to build a document builder (the factory to
			// parse DOM documents)
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// DOM Document builder, to get an empty document or parse it from
			// an xml source
			DocumentBuilder db = dbf.newDocumentBuilder();
			// Document representation in Java
			this.doc = db.parse(filepath);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO complete exception handling
		}
	}

	/**
	 * This method will search for the identities that match with the identity parameter, 
	 * and will return a list of Identities.
	 * @param Identity
	 */
	@Override
	public List<Identity> search(Identity criteria) {
		List<Identity> results = new ArrayList<Identity>();
		// gets all the nodes called "identity" (see xml/identities.xml in
		// the project)
		NodeList nodes = this.doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();

		// for every found identity
		for (int i = 0; i < nodesSize; i++) {
			Node node = nodes.item(i);
			// test if the found node is really an Element
			// in the DOM implementation a Node can represent an Element, an
			// Attribute or a TextContent
			// so we have to be sure that the found node is of type
			// "Element" using the instanceof operator
			if (node instanceof Element) {

				Identity identity = readOneIdentityFromXmlElement(node);
				// usage of Matcher to filter only the wished identities.
				if (this.matcher.match(criteria, identity)) {
					results.add(identity);
				}
			}
		}

		return results;
	}

	/**
	 * This method will list all the identities.
	 * and will return a list of identities.
	 */	
	public List<Identity> readAll(){
		List<Identity> results = new ArrayList<Identity>();
		// gets all the nodes called "identity" (see xml/identities.xml in
		// the project)
		NodeList nodes = this.doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();

		// for every found identity
		for (int i = 0; i < nodesSize; i++) {
			Node node = nodes.item(i);
			// test if the found node is really an Element
			// in the DOM implementation a Node can represent an Element, an
			// Attribute or a TextContent
			// so we have to be sure that the found node is of type
			// "Element" using the instance of operator
			if (node instanceof Element) {

				Identity identity = readOneIdentityFromXmlElement(node);
				// usage of Matcher to filter only the wished identities.
				
				results.add(identity);
			
			}
		}

		return results;
		
	}
	
	/**
	 * This method will create a new Identity with the details of the identity parameter.
	 * 
	 * @param Identity
	 */
	@Override
	public void create(Identity identity) {
		
		// Create root element and new temporary identity
		Element rootElement = doc.getDocumentElement();
		Element newIdentity = doc.createElement("identity");
		
		// List of nodes with properties of Identity
		List<Node> nodes = getIdentityElements(identity);
		
		// Add new identities
		for(int i=0; i < nodes.size(); i++){
			
			newIdentity.appendChild(nodes.get(i));	
		}

		// Add new identities to root element.
		rootElement.appendChild(newIdentity);
		
		// write modifications to file
		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		
		// Write modifications
		writeUpdatedXml(result, source);		
	}
	
	/**
	 * This method will update the identity values of the identity that matched the uid contained in the identity parameter.
	 * 
	 * @param Identity
	 */
	@Override
	public void update(Identity identity) {
		// Read all identities node.
		NodeList nodes = this.doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();

		// for every identity found
		for (int i = 0; i < nodesSize; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Identity currentIdentity = readOneIdentityFromXmlElement(node);
				// if uid searched is contained in the current node, the current node is delete 
				// and a new node is create with the update information.
				if(currentIdentity.getUid().equals(identity.getUid())){
					delete(currentIdentity);
					create(identity);
					break;	
				}
			}
		}
	}

	/**
	 * This method will delete the identity that matched the Uid contained in the Identity parameter.
	 * 
	 * @param Identity
	 */
	@Override
	public void delete(Identity identity) {
		
		// Read all identities node.
		NodeList nodes = this.doc.getElementsByTagName("identity");
		int nodesSize = nodes.getLength();

		// for every identity found
		for (int i = 0; i < nodesSize; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Identity currentIdentity = readOneIdentityFromXmlElement(node);
				// If Uid searched is contained in the current node, the current node is delete 
				if(currentIdentity.getUid().equals(identity.getUid())){
							Node temp = node.getParentNode();
							temp.removeChild(node);
							break;
				}
			
			}
		}
		// write modifications to file
		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		
		// Write modifications
		writeUpdatedXml(result, source);
		
	}

	/**
	 * This method will return an Identity object from a given node.
	 * 
	 * @param Identity
	 */
	private Identity readOneIdentityFromXmlElement(Node node) {
		// cast the node into an Element, as we are sure it is an
		// instance of Element
		Element identity = (Element) node;

		// get the properties for the encountered identity
		NodeList properties = identity.getElementsByTagName("property");
		int length = properties.getLength();

		// declare and initialize several variables on the same line
		String displayName = "", guid = "", email = "";
		Date birthDate = null;
		// for every found property
		for (int j = 0; j < length; j++) {
			Node item = properties.item(j);
			// we check that the found property is really an element
			// (see the explanation above)
			if (item instanceof Element) {
				Element propertyElt = (Element) item;
				// we need to store the right value in the right
				// property so we use a switch-case structure
				// to handle the 3 cases
				String textContent = propertyElt.getTextContent();
				switch (propertyElt.getAttribute("name")) {
				case "displayName":
					displayName = textContent;
					break;
				case "guid":
					guid = textContent;
					break;
				case "email":
					email = textContent;
					break;

				case "birthDate":
					try {
						birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(textContent);
					} catch (Exception e) {
						e.printStackTrace();
						//TODO finish exception handling
					}
					break;

				default:
					// the encountered property name is not expected
					// so we use the "default" case
					break;
				}
			}
		}
		Identity currentIdentity = new Identity(displayName, email, guid);
		currentIdentity.setBirthDate(birthDate);
		return currentIdentity;
	}
	/**
	 * This method will return the list of nodes of a given identity.
	 * 
	 * @param Identity
	 */
	
	private List<Node> getIdentityElements(Identity identity) {
		
		List<Node> nodes = new ArrayList<Node>();
		
		// Create Elements
		Element displayName = doc.createElement("property");
		Element email = doc.createElement("property");
		Element guid = doc.createElement("property");
		
		// Set Attributes
		displayName.setAttribute("name", "displayName");
		email.setAttribute("name", "email");
		guid.setAttribute("name", "guid");
		
		// Set values
		displayName.appendChild(doc.createTextNode(identity.getDisplayName()));
		email.appendChild(doc.createTextNode(identity.getEmailAddress()));
		guid.appendChild(doc.createTextNode(identity.getUid()));
		
		//Append nodes to lists
		nodes.add(displayName);
		nodes.add(email);
		nodes.add(guid);
		
		// As birthday is and optional value, check if it has been set and assign values if is neccessary
		
		if (identity.getBirthDate() != null){
			Element birthday = doc.createElement("property");
			birthday.setAttribute("name", "birthday");
			birthday.appendChild(doc.createTextNode(identity.getEmailAddress()));
			nodes.add(birthday);
		}
		
		return nodes;
	}

	/**
	 * This method will serialize the xml.
	 * 
	 * @param result
	 * @param source
	 */
	private void writeUpdatedXml(StreamResult result, DOMSource source)
	{
		try {
			// Create transformer object and set format.
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// Define source and result for the xml transformer.
			transformer.transform(source, result);
			// Load the new XML in a String variable.
			String xmlString = result.getWriter().toString();
			// Create a temporary File and write the modifications contained in the result variable..
			File File = ensureFileExists(tmpfilepath);
			PrintWriter writer = new PrintWriter(File);
			writer.print(xmlString);
			writer.close();
			// Replace the current xml with the new modified xml file.
			replaceFile(new File(filepath), File);
		} catch (IllegalArgumentException | TransformerFactoryConfigurationError | TransformerException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will check that the file exists or create it if it doesn't
	 * 
	 * @param pathname
	 * @throws IOException
	 */
	public static File ensureFileExists(String pathname) throws IOException {
		File file = new File(pathname);
		if (!file.exists()) {
			// creation code after
			System.out.println("the file does not exists");
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			file.createNewFile();
			System.out.println("file was successfully created");
		} else {
			System.out.println("the file already exists");
		}
		return file;
	}
	private File replaceFile(File oldFile, File newFile) throws IOException {
		Path oldFilePath = oldFile.toPath();
		Files.delete(oldFilePath);

		Files.move(newFile.toPath(), oldFilePath);
		return oldFile;
	}

}
