package fr.mfa.iam.lunchers;

import java.util.Random;

import fr.mfa.aim.tests.services.dao.file.IdentityFileDAO;
import fr.mfa.iam.services.dao.IdentityDAO;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int random = randomInteger(1,32000);
		System.out.println(random);
		
		IdentityDAO dao = new IdentityFileDAO();
		System.out.println(dao.readAll());
		
		
		
		
	}
	
	 public static int randomInteger(int min, int max) {
         Random rand = new Random();
         // nextInt excludes the top value so we have to add 1 to include the top value
         int randomNum = rand.nextInt((max - min) + 1) + min;
         return randomNum;
     }


}
