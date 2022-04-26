package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a single row of data in the Person table in the data base.
 */

public class Person implements Serializable {

    private int personId;
    private String firstName;
    private String lastName;
    private int libraryCard;



    public Person (int personId, String firstName, String lastName, int libraryCard) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.libraryCard = libraryCard;
    }

    public static Person newPersonFromResultSet(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4));
    }

 /*   public int getPersonId() {
        return personId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    } */

    public int getPersonId(){return personId;}

    public void setPersonId(int personId){this.personId = personId;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(){
        this.libraryCard = libraryCard;
    }

    public String[] toStringArr(Person a) {
        // declare Array of strings
        String[] personSpec = new String[4];
        personSpec[0] = (Integer.toString(personId));
        personSpec[1] = firstName;
        personSpec[2] = lastName;
        personSpec[3] = (Integer.toString(libraryCard));
        return personSpec;
    }


    @Override
    public String toString() {
        return "Person{" +
                "personID = " + personId +
                ", First Name = '" + firstName + '\'' +
                ", Last Name = " + lastName +
                ", library card = " + libraryCard +
                '}';
    }

}