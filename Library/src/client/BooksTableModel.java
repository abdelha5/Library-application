package client;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


import both.Book;


public class BooksTableModel extends AbstractTableModel {

    private String[] columnNames = { // column names for Books table
            "book_id",
            "title",
            "authors",
            "average_rating",
            "isbn",
            "isbn13",
            "language_code",
            "#num_pages",
            "ratings_count",
            "text_reviews",
            "quantity"};

    ArrayList<Book> books = new ArrayList<>(); // array list of book objects that has the books to be displayed on table
    ArrayList<String[]> fData = new ArrayList<>(); // array list of strings that will display the data to be displayed on table


    /**
     * load Data from books list of objects and add them to arraylist of Strings array
     */
    public void loadBooksFromDatabase() {
        fData.clear();

        for (int i = 0; i < books.size(); i++) {
            String[] stringData;
            Book book = books.get(i);
            stringData = book.toStringArr(book);
            fData.add(stringData);
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
