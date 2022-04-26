package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import both.*;

/**
 * Makes a connection to the SQLite database and then does operations
 * based on Client's request
 * Executes SQL Commands
 * INNER JOIN SQL commands included
 *
 */


public class SQLite {

    /**
     * Reads books table from library.SQLite
     * @return books in books table as ArrayList of Book objects
     */
    public synchronized ArrayList<Book> readBooks() {

        ArrayList<Book> books = new ArrayList<>();
        String selectSQL = "SELECT * FROM books ORDER BY book_id"; // string to indicate what type of table is wanted

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {


            // resultSet created to receive reply from db, hence I used executeQuery()
            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                books.add(Book.newBookFromResultSet(resultSet));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return books;

    }

    /**
     * Reads On Loan table from library.SQLite
     * @return On Loan books in onloan table as ArrayList of OnLoan objects
     */
    public synchronized ArrayList<OnLoan> readOnLoanTable() {

        ArrayList<OnLoan> onLoanBooks = new ArrayList<>();
        String selectSQL = "SELECT * FROM on_loan"; // string to indicate what type of table is wanted

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            ResultSet resultSet = prep.executeQuery();

            while (resultSet.next()) {
                onLoanBooks.add(OnLoan.newOnLoanFromResultSet(resultSet));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return onLoanBooks;

    }

    /**
     * Reads books table from library.SQLite
     * @return books in books table as ArrayList of Book objects
     */
    public synchronized ArrayList<Person> readPersonTable() {

        ArrayList<Person> personList = new ArrayList<>();
        String selectSQL = "SELECT * FROM person"; // string to indicate what type of table is wanted

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            ResultSet resultSet = prep.executeQuery();


            while (resultSet.next()) {
                personList.add(Person.newPersonFromResultSet(resultSet));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        return personList;

    }

    /**
     * Adds a new row to books table with data from a
     * @param a Book object that is received from client with data for row
     * @return book ID assigned to added book by SQLite db
     */

    public synchronized Book addBook(Book a) {
        Book id = null;


        String selectSQL = "INSERT INTO books (title, authors, average_rating, isbn, " +
                "isbn13, language_code, '#num_pages', ratings_count, text_reviews_count, quantity)\n" +
                "VALUES ('"
                + a.getTitle() + "', '"
                + a.getAuthor() + "', '"
                + a.getAvgRating() + "', '"
                + a.getIsbn() + "', '"
                + a.getIsbn13() + "', '"
                + a.getLanguage() + "', '"
                + a.getPages() + "', '"
                + a.getRatingsCount() + "', '"
                + a.getReviewCount() + "', '"
                + a.getQuantity() + "');"; // string to indicate what type of table is wanted
        String selectSQL2 = "SELECT * FROM books WHERE title = '" + a.getTitle() + "'";

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            prep.execute();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL2)) {

            ResultSet resultSet = prep.executeQuery();

            while(resultSet.next()){
                id = Book.newBookFromResultSet(resultSet);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(id);
        return id;
    }



    /**
     * Adds a new row to person table with data from a
     * @param a Person Object received through ObjectInputStream
     */
    public synchronized void addPerson(Person a) {

        String selectSQL = "INSERT INTO person (person_id, first_name, last_name, library_card)\n" +
                "VALUES ('"
                + a.getPersonId() + "', '"
                + a.getFirstName() + "', '"
                + a.getLastName() + "', '"
                + a.getLibraryCard() + "');"; // string to indicate what type of table is wanted

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

                    prep.execute();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Adds a new row to onloan table with data from a
     * @param a OnLoan Object received through ObjectInputStream
     */

    public synchronized void addOnLoanBook(OnLoan a) {

        String selectSQL = "INSERT INTO on_loan (loan_id, book_id, person_id, loan_period, " +
                "loan_start, loan_end, returned_date, return_status)\n" +
                "VALUES ('"
                + a.getLoanId() + "', '"
                + a.getBookId() + "', '"
                + a.getPersonId() + "', '"
                + a.getLoanPeriod() + "', '"
                + a.getLoanStart() + "', '"
                + a.getLoanEnd() + "', '"
                + a.getReturnDate() + "', '"
                + a.getStatus() + "');"; // string to indicate what type of table is wanted

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {
            prep.execute();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Deletes row of books with the book ID Integer value of String a
     * @param a String with book ID received from client through ObjectInputStream
     */
    boolean result;
    public synchronized void deleteBook(String a) {

        String selectSQL = "DELETE FROM books WHERE book_id =" + a; // string to indicate what type of table is wanted

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            ResultSet resultSet = prep.executeQuery();

            System.out.println(resultSet.toString());
            result = true;


        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
            result = false;
        }
    }


    /**
     * Deletes row of loaned books with the book ID Integer value of String a
     * @param a String with book ID received from client through ObjectInputStream
     */

    public synchronized void deleteOnLoanBook(String a) {

        String selectSQL = "DELETE FROM on_loan WHERE book_id = " + a; // string to indicate what type of table is wanted

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            prep.execute();


        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    /**
     * Deletes row of Persons with the library card Integer value of String a
     * @param a String with book ID received from client through ObjectInputStream
     */

    public synchronized void deletePerson(String a) {

        String selectSQL = "DELETE FROM person WHERE library_card = " + a; // string to indicate what type of table is wanted

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {

            prep.execute();
            System.out.println("Person with library card: " + a + "Deleted successfully");

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Unable to delete person");
        }

    }


    /**
     * receive Data from onloan and books table about books with same book ID as a
     * @param a String with book ID received from client through ObjectInputStream
     */

    public synchronized ArrayList<BookFind> findBook(int a) {

        ArrayList<BookFind> book = new ArrayList<>();
        ArrayList<Book> book2 = new ArrayList<>();

        String selectSQL = "SELECT books.book_id, on_loan.loan_id, books.title, books.authors, on_loan.return_status\n" +
                "FROM books\n" +
                "INNER JOIN on_loan\n" +
                "ON books.book_id = on_loan.book_id;"; // string to indicate what type of table is wanted

        String selectSQL2 = "SELECT * FROM books WHERE book_id = " + a;

        System.out.println(selectSQL);

        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                book.add(BookFind.newBookFindFromResultSet(resultSet));
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        int p = 0;
        for (BookFind b : book) {
            if (Integer.compare(b.getBookId(),a) == 0) {
                book.set(0, b);
                p++;
                break; // breaks loop at first encounter of book with the same ID as there are repetitive inputs of the same book in database
            }
        }
        if(p == 0){ // it would mean that book was recently added and has no data in on_loan table
            try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
                 PreparedStatement prep = conn.prepareStatement(selectSQL2)) {
                ResultSet resultSet2 = prep.executeQuery();
                while (resultSet2.next()) {
                    book2.add(Book.newBookFromResultSet(resultSet2)) ;
                }
                System.out.println("THIS --->" + book2.get(0));
                Book book3 = book2.get(0);
                System.out.println(" BOOK TITLE:" + book3.getTitle());
                book.get(0).setBookId(book3.getBookId());
                book.get(0).setTitle(book3.getTitle());
                book.get(0).setAuthor(book3.getAuthor());
                book.get(0).setReturnStatus("Returned");
                book.get(0).setLoanId(999); // default loan ID code to notify that it is not on loan
                System.out.println("SEE IF THIS WORKS" + book);
            } catch(IndexOutOfBoundsException ex){
                System.out.println("Book not Found!");
            }
            catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Book not Found!");
            }
        }
        return book;
    }

    /**
     * Receive data from person and onloan tables on rows with same library card as a
     * @param a int with library card number of person requested
     */

    public synchronized ArrayList<PersonFind> findPerson(int a) {

        ArrayList<PersonFind> person = new ArrayList<>();
        String selectSQL = "SELECT person.library_card, person.person_id, person.first_name, person.last_name,  on_loan.return_status\n" +
                "FROM person\n" +
                "INNER JOIN on_loan\n" +
                "ON person.person_id = on_loan.person_id;"; // string to indicate what type of table is wanted
        System.out.println(selectSQL);
        try (Connection conn = ConnectionFactory.getConnection(); // auto close the connection object after try
             PreparedStatement prep = conn.prepareStatement(selectSQL)) {
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {

                if (resultSet.getInt(1) == a) {
                    System.out.println("First Name: " + resultSet.getInt(5));
                    person.add(PersonFind.newPersonFindFromResultSet(resultSet));
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("THE LIST  " + person);
        return person;
    }




}