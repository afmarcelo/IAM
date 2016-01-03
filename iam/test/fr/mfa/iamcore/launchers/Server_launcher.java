/**
 * 
 */
package fr.mfa.iamcore.launchers;

import java.io.IOException;

import fr.mfa.iam.services.networking.Server;

/**
 * @author marcelo
 *
 */
public class Server_launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Server server = new Server();
		
		// Try to initiate server.
		try {
			server.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
