package fr.mfa.aim.tests.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import fr.mfa.iam.services.networking.Client;
import java.io.IOException;

/**
 * This class represents the update identity window.
 * @author marcelo
 *
 */
@SuppressWarnings("serial")
public class UpdateIdentity extends JPanel {
	
	//Labels to identify the fields
    private JLabel lbDisplayName;
    private JLabel lbEmailAddress;
    private JLabel lbUid;
    
    //Fields for data modification
    private JFormattedTextField txtDisplayName;
    private JFormattedTextField txtEmailAddress;
    private JFormattedTextField txtUid;
    
    //Fields for data modification
	private String txtEmailAddressContent ="";
    private String txtUidContent ="";
    private String txtDisplayNameContent ="";
    
    //messages
    private String updateMessage="Your identity has been update.";
    
    //Add buttons
    protected JButton btnUpdate;
    protected JButton btnExit;
    
   /**
    * Class constructor
    * @param DisplayName
    * @param EmailAddress
    * @param Uid
    */
    public UpdateIdentity(String DisplayName, String EmailAddress, String Uid){
    	super(new BorderLayout());
        //setUpFormats();
    	
    	// Create Buttons
    	btnUpdate = new JButton("Update Identity");
    	btnExit = new JButton("Exit");
        
        //Create the labels.
    	lbDisplayName = new JLabel("DisplayName");
    	lbEmailAddress = new JLabel("EmailAddress");
    	lbUid = new JLabel("Uid");
    	
    	//Create the text fields and set them up.
        txtDisplayName = new JFormattedTextField(DisplayName);
    	txtDisplayName.setColumns(20);
    	txtEmailAddress = new JFormattedTextField(EmailAddress);
    	txtEmailAddress.setColumns(20);
    	txtUid = new JFormattedTextField(Uid);
    	txtUid.setColumns(20);
    	
    	//Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(lbDisplayName);
        labelPane.add(lbEmailAddress);
        labelPane.add(lbUid);
        labelPane.add(btnExit);
 
        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(txtDisplayName);
        fieldPane.add(txtEmailAddress);
        fieldPane.add(txtUid);
        fieldPane.add(btnUpdate);
        
        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        
        // Add listeners for the buttons
        btnUpdate.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){updateButtonPressed();}});
		btnExit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exitButtonPressed();}});
    } // End constructor
    
    
    // Getters and setters for identity values
    
    public String getTxtDisplayNameContent() {
		return txtDisplayNameContent;
	}

	public void setTxtDisplayNameContent(String txtDisplayNameContent) {
		this.txtDisplayNameContent = txtDisplayNameContent;
	}

	public String getTxtEmailAddressContent() {
		return txtEmailAddressContent;
	}

	public void setTxtEmailAddressContent(String txtEmailAddressContent) {
		this.txtEmailAddressContent = txtEmailAddressContent;
	}

	public String getTxtUidContent() {
		return txtUidContent;
	}

	public void setTxtUidContent(String txtUidContent) {
		this.txtUidContent = txtUidContent;
	}
    
    protected void exitButtonPressed() {
    	Container frame = btnExit.getParent();
        do 
            frame = frame.getParent(); 
        while (!(frame instanceof JFrame));                                      
        ((JFrame) frame).dispose();		
	}

	protected void updateButtonPressed() {
		String displayName = txtDisplayName.getText();
		String emailAddress = txtEmailAddress.getText(); 
		String uid = txtUid.getText();
		Client client = new Client();
		try {
			client.connectToServer();
			client.updateIdentity(displayName, emailAddress, uid);
			client.disconnectFromServer();
			infoBox(updateMessage,"");
			exitButtonPressed();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Update Identity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new UpdateIdentity(txtDisplayNameContent, txtEmailAddressContent, txtUidContent));
 
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
}
