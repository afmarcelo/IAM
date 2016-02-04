/**
 * 
 */
package fr.mfa.iam.launchers;

import java.io.IOException;

import fr.mfa.iam.services.networking.Server;

/**
 * This class start the server program and keep listening for client connections.
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
			e.printStackTrace();
		}
		
	}

}
