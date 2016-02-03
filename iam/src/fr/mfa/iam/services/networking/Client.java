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
 * This class implement the connection logic for connecting to the IamServer. All the configuration is loaded through the configuration class.
 * The java properties starting with "cmd" represent the operations that the client can execute in the server. This operations are cmdCReate, cdmDelete, cmdReadAll and cmdAuth.
 * In order to interact with the server a simple protocol was created
 * After being authenticated, the client send a text message to the server which is divided by "::" being
 * the first column for the command to be executed and the rests columns for the parameters of the desired command.
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
    /**
     * Close current connection.
     * @throws IOException
     */
    public void disconnectFromServer() throws IOException{
    	socket.close();
    }
    /**
     * Send text message to the server.
     * @param message
     * @throws UnknownHostException
     * @throws IOException
     */
    public void sendMessage(String message) throws UnknownHostException, IOException{
    	out = new PrintWriter(socket.getOutputStream(), true);
    	out.println(message);
    }
    /**
     * Read server messages.
     * @return
     * @throws IOException
     */
    public String receiveMessage() throws IOException{
    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	String message = in.readLine();
    	return message;
    }
    /**
     * Send text command to the server.
     * @param command
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    public String sendCommand(String command) throws UnknownHostException, IOException{
    	sendMessage(command);
    	
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    /**
     * Send create Identity command to the server and read server response.
     * @param displayName
     * @param emailAddress
     * @param uid
     * @return
     * @throws IOException
     */
    public String createIdentity(String displayName, String emailAddress, String uid) throws IOException{
    	String Command = cmdCreate+cmdSeparator+displayName+cmdSeparator+emailAddress+cmdSeparator+uid;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    /**
     * Send delete Identity command to the server and read response.
     * @param displayName
     * @param emailAddress
     * @param uid
     * @return
     * @throws IOException
     */
    public String deleteIdentity(String displayName, String emailAddress, String uid) throws IOException{
    	String Command = cmdDelete+cmdSeparator+displayName+cmdSeparator+emailAddress+cmdSeparator+uid;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    /**
     * Send update Identity Command to the server and read response.
     * @param displayName
     * @param emailAddress
     * @param uid
     * @return
     * @throws IOException
     */
    public String updateIdentity(String displayName, String emailAddress, String uid) throws IOException{
    	String Command = cmdUpdate+cmdSeparator+displayName+cmdSeparator+emailAddress+cmdSeparator+uid;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    /**
     * Send Authentication request to the server, according to the local user and password received as paramenters. 
     * And wait for a server response. 
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    public String auth(String username, String password) throws IOException{
    	
    	String Command = cmdAuth+cmdSeparator+username+cmdSeparator+password;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    /**
     * Send read all Identities to the server and wait for response, the response will contain all the identities.
     * @return
     * @throws IOException
     */
    public String readAllIdentities() throws IOException{
    	
    	String Command = cmdReadAll;  
    	sendMessage(Command);
    	String response = "";
    	while(response.isEmpty()){
    		response = receiveMessage();
    		}
    	return response;
    }
    /**
     * Infobox method used, during testing and developing for validate server responses.
     * @param infoMessage
     * @param titleBar
     */
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}