package server;


import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/******************************************
 * ****************************************
 * Log on credentials:
 * User names: "abdelha5", or "chris", or "coventry"
 * password: 123456
 * Enter username without quotation marks
 * ****************************************
 * ****************************************
 * ***Before running server. You must change the users.CSV file pathname
 * String in Authentication Class to file path name on your device
 * & DB_URL String in ConnectionFactory Class to pathname on your device***
 * <p>
 * ThreadedServer Class accepts socket connections from Clients and creates
 * a ClientHandlerThread Object for each connect client
 */

public class ThreadedServer {


    private void connectToClients() {
        System.out.println("Server: Server starting.");

        try (ServerSocket serverSocket = new ServerSocket(2000)) {

            while (true) {
                System.out.println("Server: Waiting for connecting client...");

                try {
                    Socket socket = serverSocket.accept();

                    ClientHandlerThread clientHandlerThread = new ClientHandlerThread(socket);
                    Thread connectionThread = new Thread(clientHandlerThread);
                    connectionThread.start();
                } catch (IOException ex) {
                    System.out.println("Server: Could not start connection to a client.");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: Closed down");
        }
    }

    public static void main(String[] args) {
        ThreadedServer simpleServer = new ThreadedServer();
        simpleServer.connectToClients();
    }

}