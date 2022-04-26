package client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


import both.OnLoan;

/**
 * Table model class for On Loan table to display on our ClientGUI
 */

public class OnLoanTableModel extends AbstractTableModel {

    private String[] columnNames = { // column names for OnLoan Table
            "Loan ID",
            "Book ID",
            "Person ID",
            "Loan Period",
            "Loan Start",
            "Loan End",
            "Return Date",
            "Return Status"};


    ArrayList<OnLoan> onLoan = new ArrayList<>(); //  list of objects to receive
    ArrayList<String[]> fData = new ArrayList<>(); // arraylist of strings for data to be displayed on table

    /**
     * load Data from On loan list of objects and add them to arraylist of Strings array
     */

    public void loadOnLoanFromDatabase() {
        fData.clear(); // clear fData so the new data can be loaded onto the table model

        for (int i = 0; i < onLoan.size(); i++) { // for loop
            String[] stringData1;
            OnLoan onloan = onLoan.get(i);
            stringData1 = onloan.toStringArr(onloan);
            fData.add(stringData1);
        }

        fireTableDataChanged();
    }


    @Override
    public int getRowCount() {
        return fData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return fData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        fData.get(rowIndex)[columnIndex] = aValue.toString();
    }


}