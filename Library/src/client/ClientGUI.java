package client;

import javax.swing.*;

import both.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGUI extends JFrame {


    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel booksTab;
    private JPanel onLoanTab;
    private JPanel personTab;
    private JButton showBooksButton;
    private JButton showOnLoanbutton;
    private JButton showPersonButton;
    private JTable table2;
    private JTable table3;
    private JTable table1;
    private JLabel labelStatus;
    private JButton connectButton;
    private JLabel addLabel;
    private JLabel sortByField;
    private JPanel finderTab;
    private JPanel editTab;
    private JLabel chooseEditLabel;
    private JComboBox selectToEditBox;
    private JButton editApplyButton;
    private JPanel editBooksPanel;
    private JLabel addbooksLabel;
    private JTextField bookIDBookField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField AvgRatingTextField;
    private JTextField isbnField;
    private JTextField isbn13Field;
    private JTextField enterLanguageField;
    private JTextField pagesField;
    private JTextField ratingsCountField;
    private JTextField reviewsCountTextField;
    private JTextField quantityTextField;
    private JButton addBookButton;
    private JLabel bookTitleLabel;
    private JLabel authorLabel;
    private JLabel avgRatingLabel;
    private JLabel isbnLabel;
    private JLabel isbn13Label;
    private JLabel languageLabel;
    private JLabel pagesLabel;
    private JLabel ratingsCountLabel;
    private JLabel reviewCountLabel;
    private JLabel quantityLabel;
    private JLabel deleteBook;
    private JTextField deleteBookField;
    private JButton deleteBookButton;
    private JLabel deleteBookStatusLabel;
    private JPanel editOnLoanPanel;
    private JPanel personPanel;
    private JPanel editChoosePanel;
    private JPanel editPersonPanel;
    private JLabel bookIDLabel;
    private JLabel addBookStatusLabel;
    private JButton addOnLoanButton;
    private JTextField loanIdField;
    private JTextField loanBookIdField;
    private JTextField loanPersonIdFied;
    private JTextField loanPeriodField;
    private JTextField loanStartField;
    private JTextField loanEndField;
    private JTextField returnDateField;
    private JTextField returnStatusField;
    private JLabel deleteOnLoanStatusLabel;
    private JLabel addOnLoanLabel;
    private JTextField deleteOnLoanField;
    private JButton deleteOnLoanButton;
    private JTextField personIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField libraryCardField;
    private JButton addPersonButton;
    private JLabel addPersonStatusLabel;
    private JTextField deletePersonField;
    private JButton deletePersonButton;
    private JLabel deletePersonStatusLabel;
    private JLabel selectToFindLabel;
    private JComboBox toFindComboBox;
    private JButton applySelectFindButton;
    private JPanel selectToFindPanel;
    private JPanel findBookPanel;
    private JLabel findBookLabel;
    private JTextField findBookField;
    private JButton findBookButton;
    private JLabel bookIdFinderLabel;
    private JLabel loanIdFinderLabel;
    private JLabel titleFinderLabel;
    private JLabel authorFinderLabel;
    private JLabel returnStatusLabel;
    private JLabel libraryCardLabel;
    private JTextField findPersonField;
    private JButton searchPersonButton;
    private JPanel findPersonPanel;
    private JLabel personIdFindLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel onLoanPersonLabel;
    private JLabel overDueLabel;
    private JLabel findBookStatuLabel;
    private JLabel findPersonStatusLabel;
    private JComboBox returnStatusBox1;
    private JLabel label2Status;
    private JTextField userNameField;
    private JTextField passwordField;
    private JLabel uniLabel;
    private JComboBox comboBox1;
    private JScrollPane scroller1;
    private JPanel booksPanel;


    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private String sizeString;
    private int sizeInt;
    private int timesBooksLoaded = 0;
    private int timesOnLoanLoaded = 0;
    private int timesPersonLoaded = 0;

    BooksTableModel booksTableModel = new BooksTableModel();
    OnLoanTableModel onLoanTableModel = new OnLoanTableModel();
    PersonTableModel personTableModel = new PersonTableModel();


    public ClientGUI() {

        this.add(panel1);

        JScrollPane scroller = new
                JScrollPane(table1,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setSize(1000, 700);
        booksTab.setSize(10000, 10000);


        JScrollPane scroller2 = new
                JScrollPane(table2,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller2.setSize(1000, 700);
        onLoanTab.setSize(10000, 10000);

        JScrollPane scroller3 = new
                JScrollPane(table3,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller3.setSize(1000, 700);
        personTab.setSize(10000, 10000);



        table1.setAutoCreateRowSorter(true);
        table2.setAutoCreateRowSorter(true);
        table3.setAutoCreateRowSorter(true);
        editBooksPanel.setVisible(false);
        editOnLoanPanel.setVisible(false);
        editPersonPanel.setVisible(false);
        findBookPanel.setVisible(false);
        findPersonPanel.setVisible(false);
        bookIDBookField.setVisible(false);



        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // logging on or off button

                //check if client is connected or not, to execute specific request
                if(socket == null) {
                    reconnectToServer(userNameField.getText(), passwordField.getText());
                }
                else if (socket != null){
                    closeConnection();
                    labelStatus.setText("Logged Off of Portal...");
                    System.out.println("Client disconnected from server");
                }
            }
        });
        showBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    // check whether user is connected or not
                    if (socket != null) {
                        booksTableModel.books.clear();
                        table1.setVisible(true);
                        requestTable("show,books");
                        booksTableModel.loadBooksFromDatabase();
                        table1.setModel(booksTableModel);
                        timesBooksLoaded++;
                    } else {
                        labelStatus.setText("You must Log onto the portal first!!");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ClassCastException ex) {
                    System.out.println("");
                }
                if (timesBooksLoaded == 1) {
                    try {
                        booksTab.add(scroller);
                    } catch (ClassCastException | NullPointerException ex) {
                        System.out.println("scroll bar already added");
                    }
                }


            }
        });
        showOnLoanbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (socket != null) { // check if connection to server is established
                        onLoanTableModel.onLoan.clear();
                        table2.setVisible(true);
                        requestTable("show,OnLoan");
                        onLoanTableModel.loadOnLoanFromDatabase();
                        table2.setModel(onLoanTableModel);
                        timesOnLoanLoaded++;
                    } else {
                        labelStatus.setText("You must connect to the Server first!!");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                if (timesOnLoanLoaded == 1) {
                    try {
                        onLoanTab.add(scroller2, BorderLayout.CENTER);
                    } catch (ClassCastException ex) {
                        System.out.println("");
                    }
                }

            }
        });
        showPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (socket != null) {
                        personTableModel.person.clear();
                        table3.setVisible(true);
                        requestTable("show,Person");
                        personTableModel.loadPersonFromDatabase();
                        table3.setModel(personTableModel);
                        timesPersonLoaded++;
                    } else {
                        labelStatus.setText("You must connect to the server First!!");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                if (timesPersonLoaded == 1) {
                    try {
                        personTab.add(scroller3, BorderLayout.CENTER);
                    } catch (ClassCastException ex) {
                        System.out.println("");
                    }
                }

            }
        });
        editApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int j = selectToEditBox.getSelectedIndex();
                if (j == 0) {
                    editOnLoanPanel.setVisible(false);
                    editPersonPanel.setVisible(false);
                    editBooksPanel.setVisible(true);

                }
                if (j == 1) {
                    editPersonPanel.setVisible(false);
                    editBooksPanel.setVisible(false);
                    editOnLoanPanel.setVisible(true);

                }
                if (j == 2) {
                    editOnLoanPanel.setVisible(false);
                    editBooksPanel.setVisible(false);
                    editPersonPanel.setVisible(true);

                }
            }
        });

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {

                    if (objectOutputStream != null && objectInputStream != null) {

                        // 1. read data from textfield
                        // 2. send data to server
                        try {
                            Integer.parseInt(bookIDBookField.getText());
                            Float.parseFloat(AvgRatingTextField.getText());
                            Integer.parseInt(isbnField.getText());
                            Float.parseFloat(isbn13Field.getText());
                            Integer.parseInt(pagesField.getText());
                            Integer.parseInt(ratingsCountField.getText());
                            Integer.parseInt(reviewsCountTextField.getText());
                            Integer.parseInt(quantityTextField.getText());

                            objectOutputStream.writeObject(new Command("add,book"));
                            objectOutputStream.writeObject(new Book(Integer.parseInt(bookIDBookField.getText()),
                                    titleField.getText(),
                                    authorField.getText(),
                                    Float.parseFloat(AvgRatingTextField.getText()),
                                    Integer.parseInt(isbnField.getText()),
                                    Float.parseFloat(isbn13Field.getText()),
                                    enterLanguageField.getText(),
                                    Integer.parseInt(pagesField.getText()),
                                    Integer.parseInt(ratingsCountField.getText()),
                                    Integer.parseInt(reviewsCountTextField.getText()),
                                    Integer.parseInt(quantityTextField.getText())));
                            Command id = (Command) objectInputStream.readObject();
                            int t = Integer.parseInt(id.getCommand());
                            addBookStatusLabel.setText("Book Added..." + "BOOK ID IS: " + t);
                        } catch (IOException | NumberFormatException | ClassNotFoundException ex) {
                            addBookStatusLabel.setText("Error: check all fields are correct!");
                        }
                    }
                } else {
                    labelStatus.setText("You must connect to the Server first!!");
                }

            }
        });


        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // check whether client is connected...
                if (socket != null) {
                    Command reply;
                    if (objectOutputStream != null && objectInputStream != null) {

                        try {
                            objectOutputStream.writeObject(new Command("delete,book," + deleteBookField.getText()));

                            Command result = (Command) objectInputStream.readObject();

                            // command that informs system whether book was deleted or was not found

                            System.out.println("FOUND BOOK:" + result.getCommand());

                            if(result.getCommand().equals("true") == true) {
                               deleteBookStatusLabel.setText("Book Deleted...");
                                System.out.println("changed label");
                            }
                             if (result.getCommand().equals("true") == false){
                               deleteBookStatusLabel.setText("Could Not Find Book. Check Book ID is Correct!");
                                System.out.println("changed label");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            deleteBookStatusLabel.setText("check Book ID.." + ex);
                        }
                    }
                } else {
                    labelStatus.setText("You must connect to the Server first!!");
                }

            }
        });


        addOnLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {
                    if (objectOutputStream != null && objectInputStream != null) {

                        // 1. read data from textfield
                        // 2. send data to server
                        try {
                            String stat = null;
                            if(returnStatusBox1.getSelectedIndex() == 0){
                                stat = "Returned";
                            }
                            if(returnStatusBox1.getSelectedIndex() == 1){
                                stat = "On loan";
                            }
                            if(returnStatusBox1.getSelectedIndex() == 2){
                                stat = "Overdue";
                            }
                            objectOutputStream.writeObject(new Command("add,onLoan"));
                            objectOutputStream.writeObject(new OnLoan(
                                    Integer.parseInt(loanIdField.getText()),
                                    Integer.parseInt(loanBookIdField.getText()),
                                    Integer.parseInt(loanPersonIdFied.getText()),
                                    Integer.parseInt(loanPeriodField.getText()),
                                    loanStartField.getText(),
                                    loanEndField.getText(),
                                    returnDateField.getText(),
                                    stat));
                            addOnLoanLabel.setText("Book Added...");
                        } catch (IOException ex) {
                            deleteBookStatusLabel.setText("Error: check that all field are correct!" + ex);
                        }


                    }
                } else {
                    labelStatus.setText("You must connect to the Server first!!");
                }
            }
        });



        deleteOnLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {

                    if (objectOutputStream != null && objectInputStream != null) {

                        try {
                            objectOutputStream.writeObject(new Command("delete,onLoan," + deleteOnLoanField.getText()));
                            deleteOnLoanStatusLabel.setText("Book Deleted...");
                        } catch (IOException ex) {
                            deleteOnLoanStatusLabel.setText("check Book ID.." + ex);
                        }
                    }
                } else {
                    labelStatus.setText("You must connect to Server first!!");
                }

            }
        });
        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {

                    if (objectOutputStream != null && objectInputStream != null) {

                        // 1. read data from textfield
                        // 2. send data to server
                        try {
                            objectOutputStream.writeObject(new Command("add,person"));
                            objectOutputStream.writeObject(new Person(
                                    Integer.parseInt(personIdField.getText()),
                                    firstNameField.getText(),
                                    lastNameField.getText(),
                                    Integer.parseInt(libraryCardField.getText())));
                            addPersonStatusLabel.setText("Person Added...");
                        } catch (IOException ex) {
                            addPersonStatusLabel.setText("Error: check that all field are correct!" + ex);
                        }
                    }
                } else {
                    labelStatus.setText("You must connect to Server first!!");
                }
            }
        });
        deletePersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (socket != null) {

                    if (objectOutputStream != null && objectInputStream != null) {

                        try {
                            objectOutputStream.writeObject(new Command("delete,person," + deletePersonField.getText()));
                            deletePersonStatusLabel.setText("Person Deleted...");
                        } catch (IOException ex) {
                            deletePersonStatusLabel.setText("check Library card number.." + ex);
                        }
                    }
                } else {
                    labelStatus.setText("You must connect to Server first!!");
                }

            }
        });
        findBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookFind book;
                if (socket != null) {


                    if (objectOutputStream != null && objectInputStream != null) {

                        // 1. read data from textfield
                        // 2. send data to server
                        try {
                            // int id = Integer.parseInt(findBookField.getText());
                            String status = null;
                            objectOutputStream.writeObject(new Command("find,book," + findBookField.getText()));
                            book = (BookFind) objectInputStream.readObject();
                            if (book.getReturnStatus().equals("Overdue") || book.getReturnStatus().equals("On loan")) {
                                status = "Unavailable";
                            } else if (book.getReturnStatus().equals("Returned")) {
                                status = "Available";
                            }

                            System.out.println(book.getReturnStatus());
                            if (Integer.compare(book.getBookId(), Integer.parseInt(findBookField.getText())) == 0){
                                bookIdFinderLabel.setText("Book ID: " + book.getBookId());
                            loanIdFinderLabel.setText("Loan ID: " + book.getLoanId());
                            titleFinderLabel.setText("Title: " + book.getTitle());
                            authorFinderLabel.setText("Author: " + book.getAuthor());
                            returnStatusLabel.setText("Return Status: " + status);
                        }
                            if (Integer.compare(book.getLoanId(), 999) == 0) { //999 is default loan ID, meaning the book has no loan ID
                                loanIdFinderLabel.setText("Loan ID: Book is Not On Loan");
                            }
                            if (Integer.compare(book.getBookId(),Integer.parseInt(findBookField.getText())) != 0) {
                                bookIdFinderLabel.setText("Book ID: Book Not Found...");
                                loanIdFinderLabel.setText("Loan ID: Book Not Found...");
                                reconnectToServer(userNameField.getText(), passwordField.getText());
                            }

                        } catch (IOException | ClassNotFoundException | NumberFormatException ex) {
                            addPersonStatusLabel.setText("Error: check that Book ID is correct correct!");
                        }
                    }

                } else {
                    labelStatus.setText("You must connect to Server first!!");
                }

            }
        });
        applySelectFindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int j = toFindComboBox.getSelectedIndex();
                if (j == 0) {
                    findPersonPanel.setVisible(false);
                    findBookPanel.setVisible(true);
                }
                if (j == 1) {
                    findBookPanel.setVisible(false);
                    findPersonPanel.setVisible(true);
                }
            }
        });


        searchPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<PersonFind> person = new ArrayList<>();
                int overdue = 0;
                int loaned = 0;
                String name1 = null;
                String name2 = null;

                if (socket != null) {

                    if (objectOutputStream != null && objectInputStream != null) {

                        // 1. read data from textfield
                        // 2. send data to server
                        try {
                            objectOutputStream.writeObject(new Command("find,person," + findPersonField.getText()));

                            Command sz = (Command) objectInputStream.readObject();
                            sizeInt = Integer.parseInt(sz.getCommand());
                            System.out.println(sizeInt + "1");

                            for (int i = 0; i < sizeInt; i++) {
                                person.add((PersonFind) objectInputStream.readObject());
                            }
                            if(person.size() == 0){
                                personIdFindLabel.setText("Person ID: CANNOT FIND PERSON");
                            }
                            System.out.println("AMOUNT OF BOOKS: " + person.size());

                            for (PersonFind p : person) {
                                if (p.getReturnStatus().equals("Overdue")) {
                                    overdue += 1;
                                }
                                if (p.getReturnStatus().equals("On loan")) {
                                    loaned += 1;
                                }
                                personIdFindLabel.setText("Person ID: " + p.getPersonId());
                                firstNameLabel.setText("First Name: " + p.getFirstName());
                                lastNameLabel.setText("Last Name: " + p.getLastName());
                            }

                            onLoanPersonLabel.setText("Books On Loan: " + loaned);
                            overDueLabel.setText("Books Overdue: " + overdue);


                        } catch (IOException | ClassNotFoundException ex) {
                            addPersonStatusLabel.setText("Error: check that Book ID is correct correct!" + ex);
                        }
                    }
                } else {
                    labelStatus.setText("You must connect to Server first!!");
                }

            }
        });
    }


    private void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                socket = null;
                userNameField.setText("Username");
                passwordField.setText("Password");
                userNameField.setVisible(true);
                passwordField.setVisible(true);
                table1.setVisible(false);
                table2.setVisible(false);
                table3.setVisible(false);
            }
            connectButton.setText("Log On");
            label2Status.setText("Disconnected");
        }
    }


    private void reconnectToServer(String username, String password) {
        closeConnection();
        labelStatus.setText("Status: Attempting connection to server");
        try {
            socket = new Socket("127.0.0.1", 2000);
            username = userNameField.getText();
            password = passwordField.getText();
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(new UserId(username, password));
            Command authorization = (Command) objectInputStream.readObject();
            if(authorization.getCommand().contains("found")) {
                tabbedPane1.setVisible(true);
                labelStatus.setText("Logged onto portal as " + username);
                label2Status.setText("Connected: " + username);
                connectButton.setText("Log Off");
                userNameField.setVisible(false);
                passwordField.setVisible(false);
            }
            if(authorization.getCommand().contains("notFound")){
                labelStatus.setText("Incorrect Username or Password...");
                closeConnection();
            }
        }
        catch(ConnectException ex){
            labelStatus.setText("Connection Failed!" + ex);
        }
        catch (IOException ex) {
            labelStatus.setText("Connection Failed!" + ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * function to request table data from server
     *
     * @param type of table to request from server. "books", "OnLoan", or "Person"
     */

    private void requestTable(String type) throws IOException, ClassNotFoundException {

        if (objectOutputStream != null && objectInputStream != null) {

            // 1. read data from textfield
            // 2. send data to server
            try {
                objectOutputStream.writeObject(new Command(type));
            } catch (IOException ex) {
                labelStatus.setText("IOException " + ex);
            }

            // 3. recieve reply from server

            if (type == "show,books") {
                Book reply = null;
                labelStatus.setText("Status: waiting for reply from server");
                try {

                    labelStatus.setText("Status: Loading list of books");
                    Command sz = (Command) objectInputStream.readObject();
                    sizeInt = Integer.parseInt(sz.getCommand());
                    System.out.println(sizeInt + "1");
                    booksTableModel.books.clear();
                    booksTableModel.fData.clear();
                    for (int i = 0; i < sizeInt; i++) {
                        reply = (Book) objectInputStream.readObject();
                        booksTableModel.books.add(reply);
                    }

                    booksTableModel.loadBooksFromDatabase();
                    table1.setModel(booksTableModel);
                    labelStatus.setText("Status: Loaded list of books");
                } catch (IOException ex) {
                    labelStatus.setText("IOException " + ex);
                } catch (ClassNotFoundException ex) {
                    labelStatus.setText("ClassNotFoundException " + ex);
                }
                if (socket == null) {
                    labelStatus.setText("You must connect to the server first!!");
                }
            }

            if (type == "show,OnLoan") {
                OnLoan reply = null;
                labelStatus.setText("Status: waiting for reply from server");
                try {

                    labelStatus.setText("Status: Loading list of On-Loan books");
                    Command sz = (Command) objectInputStream.readObject();
                    sizeInt = Integer.parseInt(sz.getCommand());
                    System.out.println(sizeInt + "1");

                    for (int i = 0; i < sizeInt; i++) {
                        reply = (OnLoan) objectInputStream.readObject();
                        onLoanTableModel.onLoan.add(reply);
                    }

                    onLoanTableModel.loadOnLoanFromDatabase();
                    table2.setModel(onLoanTableModel);
                    labelStatus.setText("Status: Loaded list of On-Loan books");
                } catch (IOException ex) {
                    labelStatus.setText("IOException " + ex);
                } catch (ClassNotFoundException ex) {
                    labelStatus.setText("ClassNotFoundException " + ex);
                }
                if (socket == null) {
                    labelStatus.setText("You must connect to the server first!!");
                }

            }

            if (type == "show,Person") {

                Person reply = null;
                labelStatus.setText("Status: waiting for reply from server");
                try {

                    labelStatus.setText("Status: Loading list of Persons");
                    Command sz = (Command) objectInputStream.readObject();
                    sizeInt = Integer.parseInt(sz.getCommand());
                    System.out.println(sizeInt + "1");

                    for (int i = 0; i < sizeInt; i++) {
                        reply = (Person) objectInputStream.readObject();
                        personTableModel.person.add(reply);
                    }

                    personTableModel.loadPersonFromDatabase();
                    table3.setModel(personTableModel);
                    labelStatus.setText("Status: Loaded list of persons");
                } catch (IOException ex) {
                    labelStatus.setText("IOException " + ex);
                } catch (ClassNotFoundException | ClassCastException ex) {
                    labelStatus.setText("ClassNotFoundException " + ex);
                }
                if (socket == null) {
                    labelStatus.setText("You must connect to the server first!!");
                }
            }

        }
    }


    public static void main(String[] args) {
        ClientGUI pad = new ClientGUI();
        pad.pack();
        pad.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pad.setVisible(true);

    }


}
