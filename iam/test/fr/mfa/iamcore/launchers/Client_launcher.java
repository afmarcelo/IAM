/**
 * 
 */
package fr.mfa.iamcore.launchers;
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

    	Client client = new Client();
        client.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.getFrame().pack();
        client.getFrame().setVisible(true);
        
        try {
			client.connectToServer();
			client.sendMessage("hola server");
			client.sendMessage("hola server de nuevo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
