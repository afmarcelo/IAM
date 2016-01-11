package fr.mfa.aim.tests.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import fr.mfa.aim.tests.services.dao.file.IdentityFileDAO;
import fr.mfa.iam.services.dao.IdentityDAO;
import fr.mfa.iam.services.networking.Client;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.beans.PropertyChangeEvent;
import java.text.*;
 

@SuppressWarnings("serial")
public class CreateIdentity extends JPanel{

	//Labels to identify the fields
    private JLabel lbDisplayName;
    private JLabel lbEmailAddress;
    private JLabel lbUid;
    
    //Fields for data modification
    private JFormattedTextField txtDisplayName;
    private JFormattedTextField txtEmailAddress;
    private JFormattedTextField txtUid;
    
    protected JButton btnCreate;
    protected JButton btnExit;
    
    public CreateIdentity(){
    	super(new BorderLayout());
        //setUpFormats();
           
    	// Create Buttons
    	btnCreate = new JButton("Create Identity");
    	btnExit = new JButton("Create Identity");
    	
    	//Create the labels.
    	lbDisplayName = new JLabel("DisplayName");
    	lbEmailAddress = new JLabel("EmailAddress");
    	lbUid = new JLabel("Uid");
    	
    	//Create the text fields and set them up.
        txtDisplayName = new JFormattedTextField("DisplayName");
    	txtDisplayName.setColumns(20);
    	txtEmailAddress = new JFormattedTextField("EmailAddress");
    	txtEmailAddress.setColumns(20);
    	txtUid = new JFormattedTextField("Uid");
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
        setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        
        // Add listeners for the buttons
        btnCreate.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){createButtonPressed();}});
		btnExit.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exitButtonPressed();}});
    }
	
    protected void exitButtonPressed() {
		System.exit(0);
		
	}

	protected void createButtonPressed() {
		
		try {
			Client client = new Client();
			client.sendCommand("create::"+lbDisplayName.getText().toString()+"::"+lbEmailAddress.getText().toString()+"::"+lbUid.getText().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        JFrame frame = new JFrame("Create Identity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new UpdateIdentity());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
}
