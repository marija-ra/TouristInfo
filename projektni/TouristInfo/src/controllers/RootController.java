/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import analizator_rezultata.AnalizatorRezultataServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import turista.Turista;
import turisticka_mapa.Mapa;
import turisticke_atrakcije.TuristickaAtrakcija;

/**
 *
 * @author Dell
 */
public class RootController {

    public static String selectedType = "";
    public static VBox vBoxReference;
    public static ObservableList<TuristickaAtrakcija> TAObservableList = FXCollections.observableArrayList();
    public static TableView tableViewReference;
    public static File photo;
    public static File flyer;
    public static TuristickaAtrakcija selectedTA;
    public static boolean edit;
    public static final String TOUR_MAP = "turisticka-mapa.ser";
    public static Mapa mapa;
    public static ArrayList<Turista> turisti;
    public static ArrayList<Turista> turistiTop5;
    public static String takmicenjeTextArea;
    public static Stage takmicenje;
    public static Stage admin;
    public static int brojturista;
    public static AnalizatorRezultataServer rezultati;
    public static ObservableList<Turista> topTuristi = FXCollections.observableArrayList();
    public static boolean igraJeGotova;

    public static String arhiverRezultati;
    public static String letakSadrzaj;
    //public static AnalizatorRezultataServer rezultatiSaServera;
    public static Turista selektovaniTurista;

    public static boolean isCostNumber(String cost) {
        String pattern = "[0-9]{1,13}(\\.[0-9]*)?";
        return cost.matches(pattern);
    }

    public static boolean isNumber(String number) {
        System.out.println("is num" + number);
        String pattern = "[+-]?[0-9]{1,10}";
        System.out.println("number.matches(pattern)" + number.matches(pattern));
        return number.matches(pattern);
    }

    public static synchronized boolean daLiJePoslednjiTuristaUIgri() {
        RootController.brojturista++;
        System.out.println("brojturista" + RootController.brojturista);
        if (RootController.brojturista == RootController.turisti.size()) {
            return true;

        }
        return false;
    }

    public static void copyFile(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();

            }

            String[] lista = sourceLocation.list();
            for (int i = 0; i < lista.length; i++) {
                copyFile(new File(sourceLocation, lista[i]),
                        new File(targetLocation, lista[i]));

            }

        } else if (sourceLocation.isFile()) {

            InputStream in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation, true);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);

            }

            in.close();
            out.close();

        }
    }

}
