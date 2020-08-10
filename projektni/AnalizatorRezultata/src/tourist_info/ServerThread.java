/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tourist_info;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import serijalizacija.Serialization;
import turista.Turista;

/**
 *
 * @author Dell
 */
public class ServerThread extends Thread {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        start();
    }

    public void run() {

        new File("rezultati" + File.separator + "rezultati.csv").delete();

        String path = "turisti" + File.separator + "turisti_";
        FileExchanger.receiveFile(new File(path), in, out);
        ArrayList<Turista> turisti = new ArrayList<Turista>();
        turisti = Serialization.deserialize(path);

        turisti.sort(Comparator.comparing(Turista::getBrojPosjecenihTA));
        Collections.reverse(turisti);

        ArrayList<Turista> turistiTop5 = new ArrayList<Turista>(5);

        if ((turisti.size()) <= 5) {

            for (Turista a : turisti) {
                turistiTop5.add(a);
            }

        } else {

            for (int i = 0; i < 5; i++) {

                Turista c = turisti.get(i);
                turistiTop5.add(c);
            }
        }
        System.out.println("*+*+*+*+*+ TOP 5 *+*+*+*+");
        for (Turista t : turistiTop5) {
            System.out.println(t);
        }
        System.out.println("*+*+*+*+*+*+*+*+*+*+*+");

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("rezultati" + File.separator + "rezultati.csv"), true)), true);
            String linija = "";
            for (Turista t : turistiTop5) {
                linija = t.getIme() + "," + t.getBrojPosjecenihTA() + "," + t.getNovac();
                pw.println(linija);
                System.out.println(t);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            pw.close();
        }

        String topSerFileName = "topturisti_";
        Serialization.serialize(turistiTop5, "turisti", topSerFileName);
        File topSerPath = new File("turisti" + File.separator + topSerFileName);
        FileExchanger.sendFile(topSerPath, in, out);

        try {
            String csvRequest = in.readLine();
            FileExchanger.sendFile(new File("rezultati" + File.separator + "rezultati.csv"), in, out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
