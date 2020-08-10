/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serijalizacija;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Dell
 */
public class Serialization<T> {
    
    public static <T> void serialize(T object, String path, String filename) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(path + File.separator + filename)));
            oos.writeObject(object);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static <T> T deserialize(String path) {
        ObjectInputStream ois = null;
        T t = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(path)));
            //  ois = new ObjectInputStream(new FileInputStream(path));
            t = (T) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return t;
    }

    
    
}
