package fr.mfa.iam.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import fr.mfa.iam.services.networking.Client;
import java.io.IOException;
import java.net.UnknownHostException;
 
/**
 * This class creates the create identity window interface.
 * @author marcelo
 *
 */
@SuppressWarnings("serial")
public class CreateIdentity extends JPanel{

	//Labels to identify the fields
    private JLabel lbDisplayName;
    private JLabel lbEmailAddress;
    private JLabel lbUid;
    // messages 
    private String creationSuccesful="Your new identity has been created.";
    //Fields for data modification
    private JFormattedTextField txtDisplayName;
    private JFormattedTextField txtEmailAddress;
    private JFormattedTextField txtUid;
    // Buttons
    protected JButton btnCreate;
    protected JButton btnExit;
    private boolean isDone=false;
    
	public CreateIdentity(){
    	super(new BorderLayout());      
    	// Create Buttons
    	btnCreate = new JButton("Create Identity");
    	btnExit = new JButton("Exit");
    	
    	//Create the labels.
    	lbDisplayName = new JLabel("DisplayName");
    	lbEmailAddress = new JLabel("EmailAddress");
    	lbUid = new JLabel("Uid");
    	
    	//Create the text fields and set them up.
        txtDisplayName = new JFormattedTextField("");
    	txtDisplayName.setColumns(20);
    	txtEmailAddress = new JFormattedTextField("");
    	txtEmailAddress.setColumns(20);
    	txtUid = new JFormattedTextField("");
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
        fieldPane.add(btnCreate);
     
        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        
        // Add listeners for the buttons
        btnCreate.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){createButtonPressed();}});
		btnExit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exitButtonPressed();}});
    }
	// Getters and Setters
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
    protected void exitButtonPressed() {
    	Container frame = btnExit.getParent();
        do 
            frame = frame.getParent(); 
        while (!(frame instanceof JFrame));                                      
        ((JFrame) frame).dispose();
	}
	protected void createButtonPressed() {
		try {
			String displayName = txtDisplayName.getText();
			String emailAddress = txtEmailAddress.getText(); 
			String uid = txtUid.getText();
			Client client = new Client();
			client.connectToServer();
			client.createIdentity(displayName, emailAddress, uid);
			client.disconnectFromServer();
			infoBox(creationSuccesful,"");
			isDone=true;
			exitButtonPressed();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
        JFrame frame = new JFrame("Create Identity");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new CreateIdentity());
 
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
