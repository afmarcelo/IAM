package fr.mfa.aim.tests.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.*;
 

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
    
    //Add buttons
    protected JButton btnUpdate;
    protected JButton btnExit;
    
    public UpdateIdentity(){
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
        fieldPane.add(btnUpdate);
        
        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
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
        frame.add(new UpdateIdentity());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
