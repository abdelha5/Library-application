package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookFind implements Serializable {

    private int bookId;
    private int loanId;
    private String title;
    private String author;
    private String returnStatus;

    public BookFind(int bookId, int loanId, String title, String author, String returnStatus) {
        this.bookId = bookId;
        this.loanId = loanId;
        this.title = title;
        this.author = author;
        this.returnStatus = returnStatus;
    }


    public static BookFind newBookFindFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookFind(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5));
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }


}
