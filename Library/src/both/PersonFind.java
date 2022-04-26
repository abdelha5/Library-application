package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonFind implements Serializable {

    private int libraryCard;
    private int personId;
    private String firstName;
    private String lastName;
    private String returnStatus;


    public PersonFind(int libraryCard, int personId, String firstName, String lastName, String returnStatus) {
        this.libraryCard = libraryCard;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.returnStatus = returnStatus;
    }

    public static PersonFind newPersonFindFromResultSet(ResultSet resultSet) throws SQLException {
        return new PersonFind(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5));
    }


    public int getLibraryCard() {
        return libraryCard;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

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

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }


}
