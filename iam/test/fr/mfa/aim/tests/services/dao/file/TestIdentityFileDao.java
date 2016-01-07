/**
 * 
 */
package fr.mfa.aim.tests.services.dao.file;

import java.io.IOException;
import java.util.List;


import fr.mfa.aim.datamodel.Identity;
import fr.mfa.iam.exceptions.CustomException;
import fr.mfa.iam.services.dao.IdentityDAO;


/**
 * @author tbrou
 *
 */
public class TestIdentityFileDao {
	
	private static IdentityDAO dao;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		setUp();
//		testReadAll();
//		testSearch();
		
		testUpdate();
		tearDown();
			
	}

	
	private static void setUp() throws Exception{
		dao = new IdentityFileDAO();
	}
	
	private static void tearDown(){
		IdentityFileDAO identityFileDAO = (IdentityFileDAO) dao;
		identityFileDAO.close();
		
	}
	private static void testUpdate() throws Exception {
	
		List<Identity> ids = dao.readAll();
		System.out.println(ids);
		Identity foundIdentity = ids.get(0);
		
		System.out.println(foundIdentity);
		
		foundIdentity.setDisplayName("modified from test 2");
		
		dao.update(foundIdentity);
		
		System.out.println(dao.readAll());
		
		
	}
	private static void testSearch() throws IOException {
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

	private static void testReadAll() throws Exception {
		dao.create(new Identity("thomas", "tbr@tbr.com","456"));
		
		System.out.println(dao.readAll());
	}

}
