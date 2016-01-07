/**
 * 
 */
package fr.mfa.aim.tests.services.dao.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.mfa.aim.datamodel.Identity;
import fr.mfa.iam.exceptions.CustomException;
import fr.mfa.iam.services.dao.IdentityDAO;

/**
 * @author marcelo
 *
 */
public class IdentityXmlDAO implements IdentityDAO {
	
	
	public List<Identity> readAll() throws ParserConfigurationException, SAXException, IOException {
		
		// Create the result structure
		List<Identity> identities = new ArrayList<Identity>();
		
		// Create xml objects
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse("xml/identities.xml");
		NodeList nodes = doc.getElementsByTagName("identity");
		
		int nodesSize = nodes.getLength();
		for (int i = 0; i < nodesSize; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element identity = (Element) node;
				NodeList properties = identity.getElementsByTagName("property");
				int length = properties.getLength();
				
				Identity saved_identity = new Identity("", "", "");
				for (int j = 0; j < length; j++) {
					Node item = properties.item(j);
					if (item instanceof Element) {
						Element propertyElt = (Element) item;
						// Create new identity with saved values.
						
						// Save values of identity to temporal variables 
						
						if(propertyElt.getAttribute("name").equals("displayName"))
						{
							saved_identity.setDisplayName(item.getTextContent());
						}
						
						if(propertyElt.getAttribute("name").equals("email"))
						{
							saved_identity.setEmailAddress(item.getTextContent());	
						}
						
						if(propertyElt.getAttribute("name").equals("guid"))
						{
							saved_identity.setUid(item.getTextContent());
							
						}
					}
				}
				if(!saved_identity.getDisplayName().isEmpty()){
					identities.add(saved_identity);	
				}
				
				
			}
		}
		return identities;
	}

	@Override
	public List<Identity> search(Identity criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Identity identity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Identity identity) throws CustomException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Identity identity) throws CustomException {
		// TODO Auto-generated method stub
		
	}
	
}
