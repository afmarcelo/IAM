package fr.mfa.iam.services.networking;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class CreateIdentityFrame  extends JFrame{
	
	public CreateIdentityFrame(){
		super("Creating an Identity");
		JPanel form = new JPanel();
		
	 JLabel creatLabel = new JLabel("CREATING AN IDENTITY");
	 JLabel name = new JLabel("Name:");
	 JTextField nameField = new JTextField();
	 JLabel mail = new JLabel("E-mail:");
	 JTextField mailField = new JTextField();
	 JButton cancel = new JButton("Cancel");
	 JButton confirm = new JButton("Add Identity");
	 
	 
	 GridLayout layout = new GridLayout(4,2); // Two Components per row and 4 columns
	 form.setLayout(layout);
	 
	 form.add(name);
	 form.add(nameField);
	 form.add(mail);
	 form.add(mailField);
	 form.add(cancel);
	 form.add(confirm);
	 add(form); // adds the form to the JFrame
	 setSize(400, 150); // Sets the size of the JFrame
	 setResizable(false);
	
	}

}
