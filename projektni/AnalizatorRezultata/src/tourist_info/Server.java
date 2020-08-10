/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tourist_info;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Dell
 */
public class Server extends Thread  {
    private static final int SERVER_PORT = 1234;
    boolean shutdown = false;

    public void run() {
        try {
            System.out.println("running...");
            ServerSocket ss = new ServerSocket(SERVER_PORT);
            while (!shutdown) {
                System.out.println("listening...");
                Socket sc = ss.accept();
                System.out.println("Client accepted");
                ServerThread clientThread = new ServerThread(sc);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
