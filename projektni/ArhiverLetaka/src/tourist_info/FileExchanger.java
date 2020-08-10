/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tourist_info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class FileExchanger {
    
    public synchronized static void sendFile(File src, BufferedReader in, PrintWriter out) {
        ServerSocket ss = null;
        Socket sock = null;
        FileInputStream fis = null;
        try {
            ss = new ServerSocket(9004);
            out.println(new Random().nextInt(10000000));
            sock = ss.accept();
            long duzina = src.length();
            out.println(Long.toString(duzina));
            byte[] buffer = new byte[1024 * 64];
            fis = new FileInputStream(src);
            OutputStream os = sock.getOutputStream();
            int length = 0;
            int testLength = 0;
            while ((length = fis.read(buffer)) >= 0) {
                os.write(buffer, 0, length);
                testLength += length;
                os.flush();
            }
            System.out.println("Slanje zavrseno...");
            out.println(new Random().nextInt(41241241));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                fis.close();
                sock.close();
                ss.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }

    }

    public synchronized static void receiveFile(File dst, BufferedReader in, PrintWriter out) {
        ServerSocket ss = null;
        Socket sock = null;
        FileOutputStream fos = null;
        try {
            ss = new ServerSocket(9005);
            sock = ss.accept();
            String duzina = in.readLine();
            Long duzinaFajla = Long.parseLong(duzina);

            byte[] buffer = new byte[1024 * 64];
            fos = new FileOutputStream(dst);
            int kontrolnaDuzina = 0;
            InputStream is = sock.getInputStream();
            int trenutnaDuzina = 0;
            while ((trenutnaDuzina = is.read(buffer)) >= 0) {
                fos.write(buffer, 0, trenutnaDuzina);
                kontrolnaDuzina += trenutnaDuzina;
                if (kontrolnaDuzina >= duzinaFajla) {
                    break;
                }
            }
            System.out.println("Ocitavanje zavrseno...");
            String gotovo = in.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fos.close();
                sock.close();
                ss.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
