/**
 * 
 */
package fr.mfa.iam.services.networking;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fr.mfa.aim.tests.gui.SpringUtilities;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * @author marcelo
 *
 */
@SuppressWarnings("serial")
public class Table extends JPanel implements TableModel, TableModelListener{

	private JTextField filterText;
private JTextField statusText;
private TableRowSorter<Table> sorter;

    public Table() throws IOException {
        //super(new GridLayout(1,0));
    	super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Create client object and connect to the server.
        Client client = new Client();
        client.connectToServer();
      
        String data[][] = createDataArray(client.sendCommand("readall"));
        // Disconnect from the server 
        client.disconnectFromServer();
        
        // Set the columnNames in a vector.
        String[] columnNames = {"DisplayName", "Email", "UID"};
      
        // Create the table with the info collected from the server. 
        //Create a table with a sorter.
        
        final JTable table = new JTable(data, columnNames);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        // create listener for changes detection.
        table.getModel().addTableModelListener(this);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
        //Add the scroll pane to this panel.
        add(scrollPane);
        
        JPanel form = new JPanel(new SpringLayout());
        form.setPreferredSize(new Dimension(500,70));
        JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
        l1.setPreferredSize(new Dimension(100,20));
        form.add(l1);
        filterText = new JTextField();
        filterText.setPreferredSize(new Dimension(100,20));
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
        JLabel l2 = new JLabel("Status:", SwingConstants.TRAILING);
        form.add(l2);
        statusText = new JTextField();
        l2.setLabelFor(statusText);
        form.add(statusText);
        SpringUtilities.makeCompactGrid(form, 2, 2, 6, 6, 6, 6);
        add(form);
        
    }

    
    /** 
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter<Table, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @throws IOException 
     */
    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Table newContentPane = new Table();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }

    // receive all identities from the server in a single line a create a multi dimentional array need for the creation of the table. 
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
	
	// Catch modification event in the table, and throw modification event to the server.  
	public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        try {
            Client client = new Client();
        	client.connectToServer();
			
			String data_local[][]= createDataArray(client.sendCommand("readall"));
	        client.sendCommand("update::"+model.getValueAt(row,0)+"::"+model.getValueAt(row,1)+"::"+model.getValueAt(row,2));
			
	        System.out.println("modified data: "+ data);
	        
	        // 
	        
	        client.disconnectFromServer();


		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }


	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}
    
}
	
