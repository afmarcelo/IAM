/**
 * 
 */
package fr.mfa.iam.lunchers;
import java.io.IOException;

import javax.swing.JFrame;
import fr.mfa.iam.services.networking.Client;

/**
 * @author marcelo
 *
 */
public class Client_launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

    	
        
        try {
        	Client client = new Client();
			client.connectToServer();
			// client.sendMessage("hola server");
			//client.sendMessage("hola server de nuevo");
			
			// System.out.println("message receive it: " + client.receiveMessage());
			// client.sendMessage("33333");
			// while (true){
			// 	System.out.println("message receive it: " + client.receiveMessage());
			// }
			
			String response = client.sendCommand("auth;marcelo");
			System.out.println(response);
			client.disconnectFromServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
