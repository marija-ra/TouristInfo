/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizator_rezultata;

import controllers.RootController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import serijalizacija.Serialization;
import turista.Turista;

/**
 *
 * @author Dell
 */
public class AnalizatorRezultataServer extends Thread {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 1234;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private File file;

    public AnalizatorRezultataServer() {
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

        Rezultati.sendFile(file, in, out);

        File turistiTop5 = new File("turistiTop5" + File.separator + "topturisti_");

        Rezultati.receiveFile(turistiTop5, in, out);
        

        ArrayList<Turista> turisti = new ArrayList<Turista>();
        turisti = Serialization.deserialize(turistiTop5.getAbsolutePath());
        RootController.turistiTop5 = turisti;

        for (Turista t : turisti) {
            t.getBrojPosjecenihTA();
            for (Turista turista : RootController.turisti) {
                if (t.getIme().equals(turista.getIme())) {
                    t.setLeci(turista.getLeci());
                    break;
                }
            }

        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
