/**
 * 
 */
package fr.mfa.iam.lunchers;
import java.io.IOException;
import javax.swing.JOptionPane;
import fr.mfa.iam.services.networking.Client;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
			
			// Test Authentication
			String response="";
		   // response = client.sendCommand("auth::admin::epita02");
		    //infoBox(response,"Server Response");
			
		     response = client.sendCommand("create::Kevan 123 ::kevan@hotmail.com::98989898");
		     response = client.sendCommand("create::Marcelo ::marcelo@hotmail.com::sdfsdf");
		     response = client.sendCommand("create::Andrea::andrea@hotmail.com::12138");
		   	 infoBox(response,"Server Response");
			
			response = client.sendCommand("readall");
			//infoBox(response,"Server Response");
			//System.out.println(response);
			
			
			//createDataArray(response);
			
			//String multiline = "prueba\nprueba";
			///infoBox(multiline, "prueba mutiline");
			
			
			client.disconnectFromServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static String[][] createDataArray(String response) {
		String lines[] = response.split(";;",-1);
		String data[][] = new String[lines.length][3];
		for(int i=0; i<lines.length; i++){
			String fields[] = lines[i].split("::",-1);
			for(int j=0; j<fields.length; j++){
				data[i][j]=fields[j];
			}
		}
		return data;
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	//  table 
	
	

}



