/**
 * 
 */
package fr.mfa.iam.services.networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.mfa.aim.datamodel.Identity;
import fr.mfa.aim.tests.services.dao.file.IdentityFileDAO;
import fr.mfa.iam.services.dao.IdentityDAO;
/**
 * @author marcelo
 * A server program which accepts requests from clients to
 * access to the iam server remotely.  When clients connect, a new thread is
 * started to handle request from the client
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 *
 */

public class Server {
	
	
private int ServerPort = 9898;
private static String user = "admin";
private static String password = "epita01";	
	/**
     * A private thread to handle requests on a particular
     * socket.
     * @return 
     * @throws IOException 
     */
	
	public void start() throws IOException {
    	    
    	ServerSocket listener = new ServerSocket(ServerPort);
        System.out.println("IAM Server is running");
        int clientNumber = 0;
        
        try {
            while (true) {
                new session(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        } 	
    }
   
	private static class session extends Thread {
    	
    	
        private Socket socket;
        private int clientNumber;

		public session(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client.
         */
		
        public void run() {
            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                // Process input from the client, until method return false (to exit)
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals("exit")) {
                        break;
                    }
                    
                    //System.out.println(input);
                    // Process Request from client and write response
                    //String response = ""+processRequest(input);
                    //response ="sn"+response;
                    
                    out.println(processRequest(input));
                    // Response to client
                   // out.println("El server responde");
                }
                
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (Exception e) {
				e.printStackTrace();
			} finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }


        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
        	System.out.println(message);
        }
        
        private void sendMessage(String message) throws IOException{
        	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        	out.println(message);
        }
        
        
        /*
         * This method process the request. 
         */
        
		private String processRequest(String message) throws Exception{
        		
        	String[] command = message.split("::",-1);
        	
        	switch(command[0]){
        		case "auth": 
        			return authenticate(command[1], command[2]);
        		case "search":
        			return searchIdentity(createIdentityFromString(command[1], command[2], command[3]));
        		case "create":
        			return createIdentity(command[1], command[2], command[3]);
        		case "update":
        			return updateIdentity(command[1], command[2], command[3]);
        		case "delete":
        			return deleteIdentity(command[1], command[2], command[3]);
        		case "readall":	
        			return readAllIdentities();
        		default:
                	return "Invalid Command";
        	} 	
        }
        // Authenticate method, check if user and password are the provided for the thread.
		public String authenticate(String user_tmp, String password_tmp){
    		if ( user.equals(user_tmp) && password.equals(password_tmp)){
    			return "OK";
    		}else{
    			return "FAIL";
    		}	
    	}
		/**
		 * The following methods calls the Orginals DAO Methods presented in the interface. 
		 * 
		 */
		
		public Identity createIdentityFromString(String displayName, String emailAddress, String uid){
			Identity identity = new Identity(displayName, emailAddress, uid);
			return identity;
		}
		public String searchIdentity(Identity identity) throws Exception{
			
			String result="";
			IdentityDAO dao = new IdentityFileDAO();
			List<Identity> identities = dao.search(identity);
			
			for (int i=0; i< identities.size(); i++){
				result= result+identities.get(i)+"\n";
			}
			return result;
		}
		public String createIdentity(String displayName, String emailAddress, String uid) throws Exception{
			Identity identity = new Identity(displayName, emailAddress, uid);
			IdentityDAO dao = new IdentityFileDAO();
			dao.create(identity);
			return "OK";
		}
		public String updateIdentity(String displayName, String emailAddress, String uid) throws Exception{
			Identity identity = new Identity(displayName, emailAddress, uid);
			IdentityDAO dao = new IdentityFileDAO();
			dao.update(identity);
			return "OK";
		}
		public String deleteIdentity(String displayName, String emailAddress, String uid) throws Exception{
			Identity identity = new Identity(displayName, emailAddress, uid);
			IdentityDAO dao = new IdentityFileDAO();
			dao.delete(identity);
			return "OK";
		}
		public String readAllIdentities() throws Exception{
			String result="";
			IdentityDAO dao = new IdentityFileDAO();
			
			List<Identity> identities = dao.readAll();
			result = identities.toString();
			for (int i=0; i< identities.size(); i++){
			//	result= result+identities.get(i)+"\n";
			}
			return result;
		}
		
         
    }
		
		

	
}

