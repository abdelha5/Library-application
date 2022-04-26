package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents a single row of data in the Books table in the data base.
 */

public class Book extends ArrayList<Book> implements Serializable {

    private int bookId;
    private String title;
    private String author;
    private float avgRating;
    private int isbn;
    private float isbn13;
    private String language;
    private int pages;
    private int ratingsCount;
    private int reviewCount;
    private int quantity;

    public Book(int bookId, String title, String author, float avgRating, int isbn, float isbn13, String language, int pages, int ratingsCount, int reviewCount, int quantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.avgRating = avgRating;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.language = language;
        this.pages = pages;
        this.ratingsCount = ratingsCount;
        this.reviewCount = reviewCount;
        this.quantity = quantity;
    }

    public static Book newBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getFloat(4),
                resultSet.getInt(5),
                resultSet.getFloat(6),
                resultSet.getString(7),
                resultSet.getInt(8),
                resultSet.getInt(9),
                resultSet.getInt(10),
                resultSet.getInt(11));

    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public float getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(float isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String[] toStringArr(Book a) {
        // declare Array of strings
        String[] bookSpec = new String[11];
        bookSpec[0] = (Integer.toString(bookId));
        bookSpec[1] = title;
        bookSpec[2] = author;
        bookSpec[3] = (Float.toString(avgRating));
        bookSpec[4] = (Integer.toString(isbn));
        bookSpec[5] = (Float.toString(isbn13));
        bookSpec[6] = language;
        bookSpec[7] = (Integer.toString(pages));
        bookSpec[8] = (Integer.toString(pages));
        bookSpec[9] =(Integer.toString(pages));
        bookSpec[10] = (Integer.toString(pages));
        return bookSpec;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId = " + bookId +
                ", title = '" + title + '\'' +
                ", author= " + author +
                ", average Rating = " + avgRating +
                ", isbn = " + isbn +
                ", isbn13 = " + isbn13 +
                ", language code = " + language +
                ", pages = " + pages +
                ", ratings count = " + ratingsCount +
                ", review count ='" + reviewCount + '\'' +
                ", quantity" + quantity +
                '}';
    }

}