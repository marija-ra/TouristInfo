/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arhiver;

import controllers.RootController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Dell
 */
public class Arhiver extends Thread {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 4567;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Arhiver() {
        try {
            InetAddress addr = InetAddress.getByName(SERVER_ADDRESS);
            socket = new Socket(addr, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            Compress.zipDirectory(new File("turisti"), new File("zip/turisti_folderi.zip"));
            ArhiverFileExchanger.sendFile(new File("zip" + File.separator + "turisti_folderi.zip"), in, out);
            String slova = in.readLine();
            RootController.arhiverRezultati = slova;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
