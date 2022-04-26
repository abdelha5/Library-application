package client;

import java.util.Timer;
import java.util.TimerTask;
import client.ClientGUI;
/**
 * Simple demo that uses java.util.Timer to schedule a task 
 * to execute once 5 seconds have passed.
 */

public class Refresh {
    Timer timer;

    public Refresh(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {

            timer.cancel(); //Terminate the timer thread
        }
    }

    public static void main(String args[]) {
        new Refresh(5);
        System.out.println("Task scheduled.");
    }
}