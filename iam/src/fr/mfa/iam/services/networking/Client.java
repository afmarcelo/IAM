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

/**
 * @author marcelo
 * Text Client to access to the iam Server.  
 */
public class Client {
	private String serverAddress = "127.0.0.1";
	private int serverPort = 9898;
	private Socket socket;
	private BufferedReader in;
    private PrintWriter out;
    private String cmdCreate="create";
    private String cmdDelete="delete";
    private String cmdUpdate="update";
    private String cmdReadAll="readall";
    private String cmdSeparator="::";

    /**
     * Implements the connection logic by prompting the end user for
     * the server's IP address, connecting, setting up streams, and
     * consuming the welcome messages from the server.  The Capitalizer
     * protocol says that the server sends three lines of text to the
     * client immediately after establishing a connection.
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