/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arhiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Dell
 */
public class ArhiverFileExchanger {

    public static void receiveFile(File dst, BufferedReader in, PrintWriter out) {
        FileOutputStream fos = null;
        InputStream is = null;
        Socket sock = null;
        try {

            String moze = in.readLine();
            sock = new Socket(InetAddress.getByName("127.0.0.1"), 9004);
            String duzina = in.readLine();
            Long duzinaFajla = Long.parseLong(duzina);

            byte[] buffer = new byte[1024 * 64];
            fos = new FileOutputStream(dst);
            int kontrolnaDuzina = 0;
            is = sock.getInputStream();
            int trenutnaDuzina = 0;
            while ((trenutnaDuzina = is.read(buffer)) >= 0) {
                fos.write(buffer, 0, trenutnaDuzina);
                kontrolnaDuzina += trenutnaDuzina;

                if (kontrolnaDuzina >= duzinaFajla) {
                    break;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            try {
                fos.close();
                sock.close();

            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }
    }

    public static void sendFile(File src, BufferedReader in, PrintWriter out) {
        Socket sock = null;
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            sock = new Socket(InetAddress.getByName("127.0.0.1"), 9005);
            long duzina = src.length();
            out.println(Long.toString(duzina));
            byte[] buffer = new byte[1024 * 64];
            fis = new FileInputStream(src);
            os = sock.getOutputStream();
            int length = 0;
            while ((length = fis.read(buffer)) >= 0) {
                os.write(buffer, 0, length);
                os.flush();
            }

            out.println("GOTOVO");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                fis.close();
                sock.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }
}
