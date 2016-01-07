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
                    if (input.equals("1")){
                    	out.println("que uno ni que uno");	
                    }
                    //System.out.println(input);
                    out.println(input);
                    processRequest(input);
                }
                
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
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
        
        private boolean processRequest(String message){
        	
        	
        	String[] command = message.split(";",-1);
        	
        	log("command: " + command[0]);
        	log("parametre: " + command[1]);
			
        	return false;
        	
        }
        
    }
}