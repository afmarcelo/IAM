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
 * started to handle an interactive dialog menu in which the client
 * sends the selected menu option and the server thread sends back the
 * response according to the menu option selected.
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 *
 */

public class Server {
	
	/**
	 * Create variables for parameters
	 */
	
	public static String create_identity  = "1";
	public static String delete_identity = "2";
	public static String search_identity = "3";
	public static String modify_identity = "4";
	
	/**
     * A private thread to handle requests on a particular
     * socket.
     * @return 
     * @throws IOException 
     */
	
	public void start() throws IOException {
    	    
    	ServerSocket listener = new ServerSocket(9898);
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
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
            	

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                // Send menu to the client
                print_menu(out);

                // Process input from the client, until method return false (to exit)
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals("exit")) {
                        break;
                    }
                    if (input.equals("1")){
                    	out.println("que uno ni que uno");	
                    }
                    System.out.println(input);
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
         * Process option, receiving buffer reader and print writer as
         * parameters, both are associate with socket streams.
         */
		private boolean process_menu_option(BufferedReader in, PrintWriter out) throws IOException {	
			
			String input = in.readLine();
			
			// Create identity
			if ( input == create_identity){
				
				out.println("Create identity");
			
			// Delete identity	
			}else if(input == delete_identity){
				
				out.println("delete_identity");
			
			// Search identity
			}else if( input == search_identity){
				
				out.println("search_identity");
				
		    // Modify identity
			}else if( input == modify_identity){
				
				out.println("modify identity");
				
			// Exit	
			}else if( input.equals("exit")){
				
				return false;
				
			}else{
				out.println("Invalid input");
			}
			return true;
			
		}
		
		/**
		 * Print principal menu.
		 * @param out
		 */

		private void print_menu(PrintWriter out) {
			out.println("Welcome to IAM Server");
			out.println("--------------------");
			out.println("1. Create Identity");
			out.println("2. Delete Identity");
			out.println("3. Search Indentity");
			out.println("4. Modify Indentity");
			out.println("---------------------");
			out.println("Type exit to logout");
			out.println("EOF");
		}

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}