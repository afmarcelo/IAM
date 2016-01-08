/**
 * 
 */
package fr.mfa.iam.services.networking;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.table.TableModel;

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
public class Table extends JPanel implements TableModelListener{
	private boolean DEBUG = false;

    public Table() throws IOException {
        super(new GridLayout(1,0));
        
        
        // Create client object and connect to the server.
        Client client = new Client();
        client.connectToServer();
      
        // Set the columnNames in a vector.
        String[] columnNames = {"DisplayName", "Email", "UID"};
      
        // Create the table with the info collected from the server. 
        final JTable table = new JTable(createDataArray(client.sendCommand("readall")), columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        // create listener for changes detection.
        table.getModel().addTableModelListener(this);
        // Disconnect from the server 
        client.disconnectFromServer();

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
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
    
}
	
