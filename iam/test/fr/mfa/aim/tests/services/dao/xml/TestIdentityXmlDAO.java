/**
 * 
 */
package fr.mfa.aim.tests.services.dao.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.mfa.aim.datamodel.Identity;

/**
 * @author marcelo
 * This class implements the test case for the xml dao.
 *
 */
public class TestIdentityXmlDAO {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		IdentityXmlDAO dao = new IdentityXmlDAO();
		Identity identity = new Identity("Francois Hollande","hollande@hotmail.com", "00001");
		dao.delete(identity);
		System.out.println("deleted");
	
		try {
			//System.out.println(dao.readAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
