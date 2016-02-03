/**
 * 
 */
package fr.mfa.aim.tests.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import fr.mfa.iam.services.networking.Client;

/**
 * This Class represents the login form, for the first authentication.
 * @author marcelo
 *
 */
@SuppressWarnings("serial")
public class Login extends JPanel{
	
	//Labels for Username and Password
    private JLabel lbUsername;
    private JLabel lbPassword;
	
    //Fields Username and Password
    private JFormattedTextField txtUsername;
    private JPasswordField txtPassword;
	
   //Add buttons
    protected JButton btnLogin;
    protected JButton btnExit;
    
    public Login(){
    	super(new BorderLayout());
    	
    	// Create labels
        lbUsername = new JLabel("Username:");
        lbPassword = new JLabel("Password:");
    	
    	// Create Buttons
    	btnLogin = new JButton("Login");
    	btnExit = new JButton("Exit");
    	
    	//Create the text fields for Username and password
        txtUsername = new JFormattedTextField("");
    	txtUsername.setColumns(20);
    	txtPassword = new JPasswordField("");
    	txtPassword.setColumns(20);
    	
    	//Lay out the Left Panel
        JPanel leftPane = new JPanel(new GridLayout(0,1));
        leftPane.add(lbUsername);
        leftPane.add(lbPassword);
        leftPane.add(btnExit);
        
        //Layout the text fields in a panel.
        JPanel rightPane = new JPanel(new GridLayout(0,1));
        rightPane.add(txtUsername);
        rightPane.add(txtPassword);
        rightPane.add(btnLogin);

        // Put the panels in this panel, LeftPane on left,
        // RightPane on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(leftPane, BorderLayout.CENTER);
        add(rightPane, BorderLayout.LINE_END);
        
        // Add listeners for the buttons
        btnLogin.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){loginButtonPressed();}});
		btnExit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exitButtonPressed();}});
    } // End of Constructor
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("IAM Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new Login());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    /**
 	 * Create a information window message box.
 	 * @param infoMessage
 	 * @param titleBar
 	 */
 	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	protected void exitButtonPressed() {
		Container frame = btnExit.getParent();
        do 
            frame = frame.getParent(); 
        while (!(frame instanceof JFrame));                                      
        ((JFrame) frame).dispose();
	}

	protected void loginButtonPressed() {
		String response="";
		String username = txtUsername.getText();
		@SuppressWarnings("deprecation")
		String password = txtPassword.getText();	
		try {
			Client client = new Client();
			client.connectToServer();
			response = client.auth(username, password);
			client.disconnectFromServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(response.equals("OK")){
			IdentitiesTable maintable;
			try {
				maintable = new IdentitiesTable();
				maintable.createAndShowGUI();
				exitButtonPressed();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			infoBox("There was an Authenticacion error, please try again or contact your system administrator.","Authentication Error");
		}
	}
}
