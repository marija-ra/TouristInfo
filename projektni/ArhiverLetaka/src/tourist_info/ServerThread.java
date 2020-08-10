/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tourist_info;

import file_util.Compress;
import file_util.FileSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class ServerThread extends Thread{
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
        
        FileExchanger.receiveFile(new File("zip" + File.separator + "from_client.zip"), in, out);
       
        
        try {
            for (File f : new File("zip/content").listFiles()) {
                if (f.isDirectory()) {
                    for (File ff : f.listFiles()) {
                        ff.delete();
                    }
                }
                f.delete();
            }
            for (File f : new File(FileSystem.FOLDER_ARHIVA).listFiles()) {
                if (f.isDirectory()) {
                    for (File ff : f.listFiles()) {
                        ff.delete();
                    }
                }
                f.delete();
            }
            
            
            File newFolder = new File("zip" + File.separator + "content");
            newFolder.mkdir();
            Compress.unzip(new File("zip" + File.separator + "from_client.zip"), new File("zip" + File.separator + "content"));
            
            
            ArrayList<File> files = new ArrayList<>();
            new FileSystem().listf("zip" + File.separator + "content", files);
            new FileSystem().arhivirajFajlove(files);
            
            //kreiranje odgovora klijentu(statistika arhivera)
            String slova = new FileSystem().brojLetakaPoOdredjenomSlovu();
            out.println(slova);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
}
