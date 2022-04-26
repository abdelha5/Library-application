package both;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
*   Command class is used to create the communication of commands
*   between the client side GUI and the server
*
 */

public class Command implements Serializable {
    private String command;

    public Command(String command) {
        this.command = command;


    }
    public static Command newCommandFromResultSet(ResultSet resultSet) throws SQLException {
        return new Command(
                resultSet.getString(1));

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}