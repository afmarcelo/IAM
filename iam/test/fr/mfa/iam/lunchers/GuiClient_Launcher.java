package fr.mfa.iam.lunchers;

import java.io.IOException;
import fr.mfa.aim.tests.gui.Login;

/**
 * This class represents the login form, and its the start of the client side program, if authentication is ok, the followings windows are showed. 
 * @author marcelo
 *
 */
public class GuiClient_Launcher {

	public static void main(String[] args) throws IOException {
		
		// Initialized login form.
		Login login = new Login();
		login.createAndShowGUI();
		
	}

}
