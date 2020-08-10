/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class FileSystem {
    
    public static final String FOLDER_ARHIVA = "arhiva";

    public void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);

        
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }

    public void arhivirajFajlove(ArrayList<File> files) {
        
        for (File file : files) {
            System.out.println("file: " + file.getAbsolutePath());
            String nameWithExtension = file.getName();
            String extension = nameWithExtension.split("\\.")[1];
            String fileName = nameWithExtension.split("\\.")[0];
            char firstChar = fileName.charAt(0);
            String firstCharString = "" + firstChar;
            File arhivskiFolder = this.kreirajFolderAkoNePostoji(firstCharString);
            SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
            String newFileName = fileName + "_" + sdf.format(new Date()) + "_" + (System.currentTimeMillis() % 10000) + "." + extension;
            File newFile = new File(arhivskiFolder.getAbsolutePath() + File.separator + newFileName);
            this.copyFile(file, newFile);
        }
    }

    public File kreirajFolderAkoNePostoji(String folderName) {
        File arhivaFolder = new File(FOLDER_ARHIVA);
        File[] fList = arhivaFolder.listFiles();
        boolean postojiFolder = false;
        for (File f : fList) {
            if (f.getName().equals(folderName)) {
                postojiFolder = true;
                return f;
            }
        }
        if (!postojiFolder) {
            File noviFolder = new File(FOLDER_ARHIVA + File.separator + folderName);
            noviFolder.mkdir();
            return noviFolder;
        }
        return null;
    }

    public boolean copyFile(File src, File dest) {
        InputStream is = null;
        try {
            is = new FileInputStream(src);
            Files.copy(is, dest.toPath(), REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public String brojLetakaPoOdredjenomSlovu() {
        String result = "";

        File arhivaFolder = new File(FOLDER_ARHIVA);
        File[] folderList = arhivaFolder.listFiles();
        int numOfFolders = folderList.length;
        for (File folder : folderList) {
            --numOfFolders;
            String folderName = folder.getName();
            File[] fileList = folder.listFiles();
            String currentResult = folderName + ":" + fileList.length;
            result += currentResult;
            if (numOfFolders > 0) {
                result += "#";
            }
        }

        return result;
    }
    
}
