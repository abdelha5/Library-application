package server;

import both.UserId;

import java.io.*;
import java.util.*;

/**
 * The authentication class reads from the users Data CSV file
 * and compares it to userID object with data put in by user
 * to see if user exists in user file to provide access to database
 *
 */
public class Authentication {


    //change Users_CSV String to file pathname of users.CSV file on your device
    public static final String Users_CSV = "C:\\Users\\ThinkPad\\IdeaProjects\\TutorialWork\\Library\\src\\server\\users.csv";
    public static final String COMMA_DELIMITER = ",";

    /**
     * check() function to verify if user exists in CSV file
     * @param a userId object received through ObjectInputStream
     * */
    protected synchronized boolean check(UserId a) {

        boolean stat = false;
        try (BufferedReader br = new BufferedReader(new FileReader(Users_CSV))) {
            String line;
            System.out.println( a.getUsername() + a.getPassword());
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                UserId b = new UserId(values[0], values[1]);
                System.out.println(b.getUsername() + b.getPassword());
                if(a.getUsername().contains(b.getUsername()) && a.getPassword().contains(b.getPassword())){
                    stat = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Correct Credentials: " + stat);
        return stat;
    }
}