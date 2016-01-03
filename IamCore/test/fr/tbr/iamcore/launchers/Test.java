package fr.tbr.iamcore.launchers;

import java.util.Scanner;

import fr.tbr.iamcore.datamodel.Identity;


public class Test {

	
	public static void main(String[] args){
		
		System.out.println("Hello, welcome to the iam application");
			
		Scanner scanner = new Scanner(System.in);
		

		Identity identity = getIdentityFromInput(scanner);
		Identity identity2 = getIdentityFromInput(scanner);
				
		scanner.close();
		

		
	}

	private static Identity getIdentityFromInput(Scanner scanner) {
		
		System.out.println("Please, type the name for the identity");
		String identityName = scanner.nextLine();
		
		System.out.println("Please, type the email for the identity");
		String emailAddress = scanner.nextLine();

		System.out.println("Please, type the uid for the identity");
		String uid = scanner.nextLine();
				
		Identity identity = new Identity(identityName, emailAddress, uid);
		
		System.out.println("Thank you, you have input those information:");
		
		System.out.println(identity);
		
		return identity;
	}
	
}
