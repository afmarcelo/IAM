/**
 * 
 */
package fr.mfa.iam.lunchers;
import java.io.IOException;
import javax.swing.JOptionPane;

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
			
			// Test Authentication
			String response="";
		//	String response = client.sendCommand("auth::admin::epita01");
		//	infoBox(response,"Server Response");
			
		//	response = client.sendCommand("create::marcelo ardiles::marcelo@hotmail.com::12333");
		//	infoBox(response,"Server Response");
			
			response = client.sendCommand("readall");
			infoBox(response,"Server Response");
			
			String multiline = "prueba\nprueba";
			infoBox(multiline, "prueba mutiline");
			
			
			client.disconnectFromServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
