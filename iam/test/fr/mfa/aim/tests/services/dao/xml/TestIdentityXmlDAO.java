/**
 * 
 */
package fr.mfa.aim.tests.services.dao.xml;

/**
 * @author marcelo
 *
 */
public class TestIdentityXmlDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IdentityXmlDAO dao = new IdentityXmlDAO();
		
		try {
			System.out.println(dao.readAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
