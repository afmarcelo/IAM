/**
 * 
 */
package fr.mfa.iam.services.networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

import fr.mfa.aim.configuration.Configuration;

/**
 * @author marcelo
 * Text Client to access to the iam Server.  
 */
public class Client {
	private String serverAddress;
	private int serverPort;
	private Socket socket;
	private BufferedReader in;
    private PrintWriter out;
    private String cmdCreate="create";
    private String cmdDelete="delete";
    private String cmdUpdate="update";
    private String cmdReadAll="readall";
    private String cmdAuth="auth";
    private String cmdSeparator="::";
    
    /**
     * Constructor of the class load the configuration from the configuration class.
     */
    public Client(){
    	// Set configuration from configuration class.
    	Configuration config = new Configuration();
    	serverAddress = config.getServerIP();
    	serverPort = config.getServerPort();
    }

    /**
     * Implements the connection logic connecting to the server, setting up streams, and
     * consuming the server methods.  In order to interact with the server a simple protocol was created
     * After being authenticated, the client send a text message to the server which is divided by "::" being
     * the first column for the command to be executed and the rests columns for the parameters of the desired command.
     */
    
    public void connectToServer() throws IOException {

        // Make connection and initialize streams
        socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

    }
    
    public void disconnectFromServer() throws IOException{
    	socket.close();
    }
    
    public void sendMessage(String message) throws UnknownHostException, IOException{
    	out = new PrintWriter(socket.getOutputStream(), true);
    	out.println(message);
    }
    
    public String receiveMessage() throws IOException{
    	
    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	String message = in.readLine();
    	return message;
    	
    }
    
    public String sendCommand(String command) throws UnknownHostException, IOException{
    	sendMessage(command);
    	
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    
    public String createIdentity(String displayName, String emailAddress, String uid) throws IOException{
    	
    	String Command = cmdCreate+cmdSeparator+displayName+cmdSeparator+emailAddress+cmdSeparator+uid;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    
    public String deleteIdentity(String displayName, String emailAddress, String uid) throws IOException{
    	
    	String Command = cmdDelete+cmdSeparator+displayName+cmdSeparator+emailAddress+cmdSeparator+uid;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    
    public String updateIdentity(String displayName, String emailAddress, String uid) throws IOException{
    	
    	String Command = cmdUpdate+cmdSeparator+displayName+cmdSeparator+emailAddress+cmdSeparator+uid;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    
    public String auth(String username, String password) throws IOException{
    	
    	String Command = cmdAuth+cmdSeparator+username+cmdSeparator+password;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    
    public String readAllIdentities() throws IOException{
    	
    	String Command = cmdReadAll;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}