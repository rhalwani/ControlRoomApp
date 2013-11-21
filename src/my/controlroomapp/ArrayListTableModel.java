package my.controlroomapp;

import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ArrayListTableModel
        implements TableModel {

    ArrayList results;
    ArrayList<String> metadata;
    int numcols;
    int numrows;

    ArrayListTableModel(ArrayList headers, ArrayList results) {
        this.results = results;
        this.metadata = headers;
        this.numcols = this.metadata.size();
        this.numrows = results.size();
    }

    @Override
    protected void finalize() {
    }

    @Override
    public int getColumnCount() {
        return this.numcols;
    }

    @Override
    public int getRowCount() {
        return this.numrows;
    }

    @Override
    public String getColumnName(int column) {
        return (String) this.metadata.get(column);
    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Object o = ((CreditTransModel) this.results.get(row)).getRowEntry().get(column);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
}
