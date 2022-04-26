package client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;



import both.Person;



public class PersonTableModel extends AbstractTableModel {

    private String[] columnNames = {"Person ID","First Name","Last Name","Library Card"};


    ArrayList<Person> person = new ArrayList<>();
    ArrayList<String[]> fData = new ArrayList<>();

    public void saveToDatabase() {
        // grab data structure

        // store data into database.
        //Formatter formatter = new Formatter(new File("T5-Swing\\myDatabase.csv"));
        //formatter

    }

    /**
     * Read our text file database.  Populate our JTable data-structure.
     */

    public void loadPersonFromDatabase() {
        fData.clear();


        for (int i = 0; i < person.size(); i++) {
            String[] stringData1;
            Person toSend = person.get(i);
            stringData1 = toSend.toStringArr(toSend);
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