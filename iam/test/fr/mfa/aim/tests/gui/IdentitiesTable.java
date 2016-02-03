package fr.mfa.aim.tests.gui;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.IOException;
import fr.mfa.aim.tests.gui.SpringUtilities;
import fr.mfa.iam.services.networking.Client;

/**
 * This class represent the identity table interface.
 * @author marcelo
 */
@SuppressWarnings("serial")
public class IdentitiesTable extends JPanel implements TableModelListener, WindowFocusListener{

	private JTable table;
	private MyTableModel model;
    private JTextField filterText;
    private TableRowSorter<MyTableModel> sorter;
    protected JButton deleteButton, modifyButton, createButton, exitButton,refreshButton;
    private Integer SelectedRow;
    
    public IdentitiesTable() throws IOException {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Connect to server and request all identities that will be populated in the table. 
        Client client = new Client();
        client.connectToServer();
        String data[][]=createDataArray(client.sendCommand("readall"));
        client.disconnectFromServer();
        
        //Create a table with a sorter.
        model = new MyTableModel();
        model.setData(data);
        sorter = new TableRowSorter<MyTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setFillsViewportHeight(true);
        
        //When selection changes, provide user with row numbers for
        //both view and model.
        table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                           
                        } else {
                            int modelRow = table.convertRowIndexToModel(viewRow);
                            SelectedRow = viewRow;
                        }
                    }
                }
        );
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        
        //Add the scroll pane to this panel.
        add(scrollPane);

        //Create a separate form for filterText and buttons statusText
        JPanel form = new JPanel(new SpringLayout());
        JLabel l1 = new JLabel("Filter Identity:", SwingConstants.TRAILING);
        form.add(l1);
        filterText = new JTextField();
      
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
        
        l1.setLabelFor(filterText);
        form.add(filterText);
                
        // Add Create Labels, empty labels are created in order to balance the form layout.
		JLabel l4 = new JLabel();
		JLabel l5 = new JLabel();
		JLabel l6 = new JLabel();
		
        // Create Buttons
		refreshButton = new JButton("Refresh Table");
		createButton = new JButton("Create Identity");
		deleteButton = new JButton("Delete Selected Identity");
        modifyButton = new JButton("Modify Selected Identity");
        exitButton = new JButton("Exit");
        
        // Add Listener to buttons
		deleteButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){deleteButtonPressed();}});
		createButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){createButtonPressed();}});
		modifyButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){modifyButtonPressed();}});
		exitButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){exitButtonPressed();}});
		refreshButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){refreshButtonPressed();}});
		
		// Add Buttons to form
		form.add(refreshButton);
		form.add(createButton);
		form.add(l4);
		form.add(modifyButton);
		form.add(l5);
		form.add(deleteButton);
		form.add(l6);
		form.add(exitButton);
        
        // Define a form layout of 6 rows and 2 columns. 
        SpringUtilities.makeCompactGrid(form, 5, 2, 6, 6, 6, 6);
        
        // Add Form
        add(form);
        
    }
    
    protected void refreshButtonPressed() {
		refreshTable();		
	}

	/**
     * Exit the application when ExitButton is pressed. 
     */
    protected void exitButtonPressed() {
    	System.exit(0);
	}
    
    /**
     * Call modify selected identity when modifyButton is pressed.
     */
	protected void modifyButtonPressed() {
		// Save information from the selected Identity.
		
		if (SelectedRow == null){			
			infoBox("ERROR: Select one identity", "ERROR");
		}else{
		
		String displayName = (String) table.getValueAt(SelectedRow, 0);
		String emailAddress = (String) table.getValueAt(SelectedRow, 1);
		String uid = (String) table.getValueAt(SelectedRow, 2);
		
		System.out.println(displayName);
		System.out.println(emailAddress);
		System.out.println(uid);
		
		UpdateIdentity updateidentity = new UpdateIdentity(displayName, emailAddress, uid);
		updateidentity.setTxtDisplayNameContent(displayName);
		updateidentity.setTxtEmailAddressContent(emailAddress);
		updateidentity.setTxtUidContent(uid);
		updateidentity.createAndShowGUI();
		}
	}
	/**
	 * Call create identity method when createButton is pressed.
	 */
	protected void createButtonPressed()  {	   

		Runnable CreateIdentity = new Runnable() {
		     public void run() {
		    	 	CreateIdentity createidentity= new CreateIdentity();
					createidentity.createAndShowGUI();
		     }
		 };
		 
		SwingUtilities.invokeLater(CreateIdentity);
     
	}
	/**
	 * Delete selected identity when delete button is pressed.
	 * 
	 */
	protected void deleteButtonPressed() {
		// Save information from the selected Identity.
		String displayName = (String) table.getValueAt(SelectedRow, 0);
		String emailAddress = (String) table.getValueAt(SelectedRow, 1);
		String uid = (String) table.getValueAt(SelectedRow, 2);
		//Connect to server and send command
		try {
			Client client = new Client();
			client.connectToServer();
			client.deleteIdentity(displayName, emailAddress, uid);
			client.disconnectFromServer();
			refreshTable();
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
	}

	/**
	 * Refresh content of table from the server.
	 * 
	 */
	
	private void refreshTable() {
		Client client = new Client();
		try {
			client.connectToServer();
			String updatedData[][]=createDataArray(client.readAllIdentities());
			model.setData(updatedData);
			table.repaint();
			model.fireTableDataChanged();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter<MyTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    class MyTableModel extends AbstractTableModel {
    	private String[] columnNames = {"DisplayName", "emailAddress", "UID" };
    	private Object[][] data; 
        public String[] getColumnNames() {
			return columnNames;
		}
		public void setColumnNames(String[] columnNames) {
			this.columnNames = columnNames;
		}
		public Object[][] getData() {
			return data;
		}
		public void setData(Object[][] data) {
			this.data = data;
		}
		public int getColumnCount() {
            return columnNames.length;
        }
        public int getRowCount() {
            return data.length;
        }
        public String getColumnName(int col) {
            return columnNames[col];
        }
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        public boolean isCellEditable(int row, int col) {
            //Only 1st and 2nd column are editables.
            if (col >= 0) {
                return false;
            } else {
                return true;
            }
        }
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @throws IOException 
     */
    public void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("Identities Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add windows listener
        frame.addWindowListener(new WindowListener(){ public void windowActivated(WindowEvent e) {
        	
        System.out.println("WindowListener method called: windowActivated.");
        refreshTable();
        }

		@Override
		public void windowClosed(WindowEvent arg0) {
			
		}
		@Override
		public void windowClosing(WindowEvent arg0) {
			
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {
		
		}
		@Override
		public void windowDeiconified(WindowEvent arg0) {
			
		}
		@Override
		public void windowIconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			
		}});
        //Create and set up the content pane.
        IdentitiesTable newContentPane = new IdentitiesTable();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /** receive all identities from the server in a single line a create a multi dimentional array need for the creation of the table.
     *  Response String should contain ;; separator for identify Identities and :: separator for identity Identity Fields
     * @param response
     * @return multi dimentional array with the identities information. 
     */
 	private static String[][] createDataArray(String response) {
 		String lines[] = response.split(";;",-1);
 		String data[][] = new String[lines.length][3];
 		for(int i=0; i<lines.length; i++){
 			String fields[] = lines[i].split("::",-1);
 			for(int j=0; j<fields.length; j++){
 				data[i][j]=fields[j];
 			}
 		}
 		return data;
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

	@Override
	public void tableChanged(TableModelEvent arg0) {
		
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
	
		refreshTable();
		
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		
	}
 	
	}
