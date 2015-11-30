/**
 * 
 */
package fr.tbr.iamcore.tests;

import java.io.IOException;
import java.util.List;

import fr.tbr.iamcore.datamodel.Identity;
import fr.tbr.iamcore.tests.services.dao.IdentityFileDAO;


/**
 * @author tbrou
 *
 */
public class TestIdentityFileDao {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		testReadAll();
//		testSearch();
		
		

	}

	private static void testSearch() throws IOException {
		IdentityFileDAO dao = new IdentityFileDAO();
		dao.create(new Identity("clement", "clem@clem.com", "789"));
		
		Identity criteria = new Identity("cle", "clem@", null);
		List<Identity> results = dao.search(criteria);
		System.out.println("this should contain at least one result");
		System.out.println(results);
		
		
		Identity notFoundCriteria = new Identity("seb", "sebastien.marre@seb.com", null);
		System.out.println("this should contain no result");
		results = dao.search(notFoundCriteria);
		System.out.println(results);
	}

	private static void testReadAll() throws IOException {
		IdentityFileDAO dao = new IdentityFileDAO();
		dao.create(new Identity("thomas", "tbr@tbr.com","456"));
		
		System.out.println(dao.readAll());
	}

}
