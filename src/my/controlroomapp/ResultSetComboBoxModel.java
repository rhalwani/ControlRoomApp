/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.controlroomapp;

/**
 *
 * @author Riad
 */

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetComboBoxModel implements ComboBoxModel {

    ResultSet results;             // The ResultSet to interpret
    ResultSetMetaData metadata;    // Additional information about the results
    int numrows;          // How many rows and columns in the table
    
    public ResultSetComboBoxModel(ResultSet result) throws SQLException {
	this.results = results;                 // Save the results
	metadata = results.getMetaData();       // Get metadata on them
	results.last();                         // Move to last row
	numrows = results.getRow();             // How many rows? (Combo Box has only 1 Column)
    }

    public Object getSelectedItem() {
        return null;
    }

    public void setSelectedItem(Object item) {

    }

    public void removeListDataListener(ListDataListener l) {

    }

    public void addListDataListener(ListDataListener l) {

    }

    public Object getElementAt(int i) {
        return null;
    }

    public int getSize() {
        return 1;
    }
    
    public void close() {
	try { results.getStatement().close(); }
	catch(SQLException e) {};
    }
    
    /** Automatically close when we're garbage collected */
    @Override
    protected void finalize() { close(); }
}
