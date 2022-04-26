package both;

import java.io.Serializable;

/**
 * User ID class used for user authentication
 * a User ID object with username and password that user put in
 * will be compared to User ID object array list of data gathered from Users CSV
 * in the server package, to authenticate user.
 */
public class UserId implements Serializable {
    private String username;
    private String password;


    public UserId(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
