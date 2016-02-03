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
import java.util.List;
import fr.mfa.aim.configuration.Configuration;
import fr.mfa.aim.datamodel.Identity;
import fr.mfa.aim.datamodel.User;
import fr.mfa.aim.tests.services.dao.xml.IdentityXmlDAO;
import fr.mfa.iam.services.dao.IdentityDAO;
/**
 * @author marcelo
 * This class implements a server program which accepts requests from clients to
 * access to the iam server remotely.  When clients connect, a new thread is
 * started to handle request from the client.
 */
public class Server {
private int ServerPort;
/**
 * Create a static dao property, which will be used for implementing the commands requested from the authenticate client side. 		
 */
private static IdentityDAO dao;	
	/**
	 * Serve constructor, initialize configuration obtained from the configuration class.
	 */
	public Server(){
		// Set the configuration from Configuration Class
		Configuration config = new Configuration();
		ServerPort = config.getServerPort();
		// Define the DAO which will be used.
		try {
			dao = new IdentityXmlDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start the server according to the configuration.
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
   
	/**
     * Create private thread to handle requests on a particular
     * socket. Due to is possible connect to the server from multiples clients.
     * @return 
     * @throws IOException 
     */
	private static class session extends Thread {
        private Socket socket;
        private int clientNumber;
        /**
         * Create a session for each client.
         * @param socket
         * @param clientNumber
         */
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
                    
                    // Process the message received from the client side and respond writing the client buffer.
                    out.println(processRequest(input));
                }
                
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (Exception e) {
				e.printStackTrace();
			} finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket");
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
        /*
         * This method process the request. 
         */
		/**
		 * Process messages from the clients and launch command to be execute on the local dao.
		 * @param message
		 * @return
		 * @throws Exception
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
        /**
         * Authenticate method, check if user and password are the provided for the thread.
		 */
        public String authenticate(String user_tmp, String password_tmp){
			// create local temp User object with the values passed as parameters
			User user = new User("","","");
			user.setUsername(user_tmp);
			user.setPassword(password_tmp);
			// search for user.
			User response = dao.searchUser(user);
			// if a username has been found according to the search criteria evaluate if password is correct and send response.
			if(response !=null){
				if(response.getPassword().equals(user.getPassword())){
					return "OK";
				}else{
					return "FAIL";
				}		
			}else{ // If response is null, then there is no user with the specified username and it is returned a fail message.
				return "FAIL";
			}	
    	}
		/**
		 * The following methods calls the Orginals DAO Methods presented in the interface. 
		 * 
		 */
		/**
		 * Create identity.
		 * @param displayName
		 * @param emailAddress
		 * @param uid
		 * @return
		 */
		public Identity createIdentityFromString(String displayName, String emailAddress, String uid){
			Identity identity = new Identity(displayName, emailAddress, uid);
			return identity;
		}
		/**
		 * Search for and identity.
		 * @param identity
		 * @return
		 * @throws Exception
		 */
		public String searchIdentity(Identity identity) throws Exception{
			
			String result="";
			List<Identity> identities = dao.search(identity);
			
			for (int i=0; i< identities.size(); i++){
				result= result+identities.get(i)+"\n";
			}
			return result;
		}
		/**
		 * Create and identity.
		 * @param displayName
		 * @param emailAddress
		 * @param uid
		 * @return
		 * @throws Exception
		 */
		public String createIdentity(String displayName, String emailAddress, String uid) throws Exception{
			Identity identity = new Identity(displayName, emailAddress, uid);
			
			dao.create(identity);
			return "OK";
		}
		/**
		 * Update identity
		 * @param displayName
		 * @param emailAddress
		 * @param uid
		 * @return
		 * @throws Exception
		 */
		public String updateIdentity(String displayName, String emailAddress, String uid) throws Exception{
			Identity identity = new Identity(displayName, emailAddress, uid);
			dao.update(identity);
			return "OK";
		}
		/**
		 * Delete identity.
		 * @param displayName
		 * @param emailAddress
		 * @param uid
		 * @return
		 * @throws Exception
		 */
		public String deleteIdentity(String displayName, String emailAddress, String uid) throws Exception{
			Identity identity = new Identity(displayName, emailAddress, uid);
			dao.delete(identity);
			return "OK";
		}
		/**
		 * Read all identities
		 * @return
		 * @throws Exception
		 */
		public String readAllIdentities() throws Exception{
			String result="";	
			List<Identity> identities = dao.readAll();
			// result = identities.toString();
			
			for (int i=0; i< identities.size(); i++){
				result= result+identities.get(i).getDisplayName()+"::"+identities.get(i).getEmailAddress()+"::"+identities.get(i).getUid()+";;";
			}
			return result;
		}
    }
}

