package server;

import both.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;


/**
 * This is the thread class with the responsibility of handling client requests
 * once the client has connected. A socket is stored to allow connection.
 * <p>
 * I used the method of implenting the runnable interface to make a thread,
 * as I do not have to waste our inheritance option, for future improvements.
 */
public class ClientHandlerThread implements Runnable {

    private final Socket socket;


    private static int connectionCount = 0;
    private final int connectionNumber;
    Book book;
    String delete;
    OnLoan onLoanBook;
    Person person;


    /**
     * Constructor just initialises the connection to client.
     *
     * @param socket the socket to establish the connection to client.
     * @throws IOException if an I/O error occurs when creating the input and
     *                     output streams, or if the socket is closed, or socket is not connected.
     */
    public ClientHandlerThread(Socket socket) throws IOException {
        this.socket = socket;


        connectionCount++;
        connectionNumber = connectionCount;
        threadSays("Connection " + connectionNumber + " established.");
    }


    @Override
    public void run() {
        try (
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ) {


            // Read and process names until an exception is thrown.
            System.out.println("Server: Waiting for login credentials from client...");
            Command commandRead;
            UserId userId = null;
            Authentication authenticate = new Authentication();
            SQLite sql = new SQLite();

            // Server first receives a command type object with instructions of what to do
            try {

                while ((userId = (UserId) objectInputStream.readObject()) != null) {

                    boolean found = authenticate.check(userId); // found user credentials in CSV file??
                    threadSays("Correct login Credentials: " + found +" User: " + userId.getUsername());

                    if (found == true) {

                        threadSays("Client " + userId.getUsername() + " logged onto Portal");

                        objectOutputStream.writeObject(new Command("found"));

                        while ((commandRead = (Command) objectInputStream.readObject()) != null) {

                            threadSays("Server: Read data from client" + userId.getUsername() + " : " + commandRead + ".");
                            String[] interpretation = commandRead.toString().split(",");

                            //If statement to determine whether command wants to ask for table data. "show".

                            if (interpretation[0].contains("show")) {

                                // second command word is read to check which table was requested

                                // displays books table
                                if (interpretation[1].contains("books")) {

                                    List<Book> bookList = sql.readBooks();
                                    String replyMessage = (Integer.toString(bookList.size()));
                                    objectOutputStream.writeObject(new Command(replyMessage));


                                    for (int i = 0; i <= bookList.size() - 1; i++) {
                                        Book j = bookList.get(i);
                                        objectOutputStream.writeObject(new Book(j.getBookId(),
                                                j.getTitle(),
                                                j.getAuthor(),
                                                j.getAvgRating(),
                                                j.getIsbn(),
                                                j.getIsbn13(),
                                                j.getLanguage(),
                                                j.getPages(),
                                                j.getRatingsCount(),
                                                j.getReviewCount(),
                                                j.getQuantity()));
                                    }
                                    threadSays("Number of books sent to" + userId.getUsername() + ": " + bookList.size());
                                }


                                // display on-loan table
                                if (interpretation[1].contains("OnLoan")) {

                                    List<OnLoan> onLoanlist = sql.readOnLoanTable();
                                    String replyMessage = (Integer.toString(onLoanlist.size()));
                                    objectOutputStream.writeObject(new Command(replyMessage));


                                    for (int i = 0; i <= onLoanlist.size() - 1; i++) {
                                        OnLoan j = onLoanlist.get(i);
                                        objectOutputStream.writeObject(new OnLoan(j.getLoanId(),
                                                j.getBookId(),
                                                j.getPersonId(),
                                                j.getLoanPeriod(),
                                                j.getLoanStart(),
                                                j.getLoanEnd(),
                                                j.getReturnDate(),
                                                j.getStatus()));
                                    }
                                    threadSays("Number of books on Loan sent to " + userId.getUsername() + ": " + onLoanlist.size());
                                }

                                // retrieval of data from persons table
                                if (interpretation[1].contains("Person")) {

                                    List<Person> personList = sql.readPersonTable();
                                    String replyMessage = (Integer.toString(personList.size()));
                                    objectOutputStream.writeObject(new Command(replyMessage));


                                    for (int i = 0; i <= personList.size() - 1; i++) {
                                        Person j = personList.get(i);
                                        objectOutputStream.writeObject(new Person(
                                                j.getPersonId(),
                                                j.getFirstName(),
                                                j.getLastName(),
                                                j.getLibraryCard()
                                        ));
                                    }
                                    threadSays("Number of persons sent: " + personList.size());
                                }
                            }

                            //if loop to run adding of rows to data base tables with command word "add"

                            if (interpretation[0].contains("add")) {

                                //adding a book
                                if (interpretation[1].contains("book")) {

                                    book = (Book) objectInputStream.readObject();
                                    Book id = sql.addBook(book);
                                    System.out.println("IF BOOK ID IS CORRECT: " + id);
                                    objectOutputStream.writeObject(new Command(String.valueOf(id.getBookId())));
                                }

                                if (interpretation[1].contains("onLoan")) {

                                    onLoanBook = (OnLoan) objectInputStream.readObject();
                                    sql.addOnLoanBook(onLoanBook);
                                }

                                if (interpretation[1].contains("person")) {

                                    person = (Person) objectInputStream.readObject();
                                    sql.addPerson(person);
                                }

                            }
                            if (interpretation[0].contains("delete")) {
                                if (interpretation[1].contains("book")) {

                                    delete = interpretation[2];
                                    sql.deleteBook(delete);
                                    if (sql.result == false) {
                                        objectOutputStream.writeObject(new Command("false"));
                                    }
                                    if (sql.result == true) {
                                        objectOutputStream.writeObject(new Command("true"));
                                    }
                                    threadSays("FOUND BOOK:" + sql.result);
                                }

                                if (interpretation[1].contains("onLoan")) {

                                    delete = interpretation[2];
                                    sql.deleteOnLoanBook(delete);
                                }

                                if (interpretation[1].contains("person")) {

                                    delete = interpretation[2];
                                    sql.deletePerson(delete);
                                }

                            }

                            if (interpretation[0].contains("find")) {

                                if (interpretation[1].contains("book")) {
                                    int err = 0;
                                    try {
                                        int i = Integer.parseInt(interpretation[2]);
                                        List<BookFind> reply = sql.findBook(i);
                                        threadSays("Search book with ID: " + interpretation[2]);

                                        System.out.println("BOOK TO BE SENT" + reply);
                                        for (BookFind bl : reply) {
                                            objectOutputStream.writeObject(new BookFind(
                                                    bl.getBookId(),
                                                    bl.getLoanId(),
                                                    bl.getTitle(),
                                                    bl.getAuthor(),
                                                    bl.getReturnStatus()));
                                            threadSays("book sent");
                                        }
                                    } catch (IndexOutOfBoundsException ex) {
                                        err++;
                                        threadSays("Could not Find book in Database");
                                    } catch (NullPointerException ex) {
                                        threadSays("Could not Find book in Database");
                                        err++;
                                    }

                                    if (err > 0) {
                                        objectOutputStream.writeObject(new BookFind(
                                                0,
                                                0,
                                                "0",
                                                "0",
                                                "0"));
                                    }
                                }

                                if (interpretation[1].contains("person")) {
                                    int j = Integer.parseInt(interpretation[2]);
                                    List<PersonFind> bookList = sql.findPerson(j);
                                    String replyMessage = (Integer.toString(bookList.size()));
                                    objectOutputStream.writeObject(new Command(replyMessage));


                                    for (int i = 0; i <= bookList.size() - 1; i++) {
                                        PersonFind c = bookList.get(i);
                                        objectOutputStream.writeObject(new PersonFind(
                                                c.getLibraryCard(),
                                                c.getPersonId(),
                                                c.getFirstName(),
                                                c.getLastName(),
                                                c.getReturnStatus()));
                                        threadSays("Person Loan History sent");
                                    }
                                }
                            }
                        }
                    }
                    if (found == false) {
                        threadSays("INCORRECT CREDENTIALS PUT IN");
                        objectOutputStream.writeObject(new Command("notFound"));
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                threadSays("ERROR: " + ex);
            } catch (SocketException | EOFException ex) {
                threadSays("Client " + userId.getUsername() + " disconnected from Portal");
            }
        } catch (SocketException ex) {
            threadSays("Error" + ex);
        } catch (IOException | ClassNotFoundException ex) {
            threadSays("Error: " + ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                threadSays("Error: " + ex);
            }
        }


    }

    /**
     * Private helper method outputs to standard output stream for debugging.
     *
     * @param say the String to write to standard output stream.
     */
    private void threadSays(String say) {
        System.out.println("ClientHandlerThread" + connectionNumber + ": " + say);
    }
}