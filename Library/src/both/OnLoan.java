package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
/**
 * This class represents a single row of data in the On-loan table in the data base.
 */

public class OnLoan implements Serializable {

    private int loanId;
    private int bookId;
    private int personId;
    private int loanPeriod;
    private String loanStart;
    private String loanEnd;
    private String returnDate;
    private String status;


   /* private enum Status {
        ON_LOAN,
        RETURNED,
        OVERDUE,
    } */

    public OnLoan (int loanId, int bookId, int personId, int loanPeriod, String loanStart, String loanEnd, String returnDate, String status) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.personId = personId;
        this.loanPeriod = loanPeriod;
        this.loanStart = loanStart;
        this.loanEnd = loanEnd;
        this.returnDate = returnDate;
        this.status = status;


       /* if(status == "On loan"){
        Status books = Status.ON_LOAN;
        }*/
    }

    public static OnLoan newOnLoanFromResultSet(ResultSet resultSet) throws SQLException {
        return new OnLoan(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8));
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getLoanStart() {
        return loanStart;
    }

    public void setLoanStart(String loanStart) {
        this.loanStart = loanStart;
    }

    public String getLoanEnd() {
        return loanEnd;
    }

    public void setLoanEnd(String loanEnd) {
        this.loanEnd = loanEnd;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String[] toStringArr(OnLoan a) {
        // declare Array of strings
        String[] onLoanSpec = new String[8];
        onLoanSpec[0] = (Integer.toString(loanId));
        onLoanSpec[1] = (Integer.toString(bookId));
        onLoanSpec[2] = (Integer.toString(personId));
        onLoanSpec[3] = (Integer.toString(loanPeriod));
        onLoanSpec[4] = loanStart;
        onLoanSpec[5] = loanEnd;
        onLoanSpec[6] = returnDate;
        onLoanSpec[7] = status;
        return onLoanSpec;
    }


    @Override
    public String toString() {
        return "OnLoan{" +
                "loanID = " + loanId +
                ", bookID = '" + bookId + '\'' +
                ", personID = " + personId +
                ", loan period = " + loanPeriod +
                ", loan start = " + loanStart +
                ", loan end = " + loanEnd +
                ", return date = " + returnDate +
                ", return status = " + status +
                '}';
    }

}