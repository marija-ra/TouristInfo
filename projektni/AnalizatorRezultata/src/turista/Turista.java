/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turista;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author Dell
 */
public class Turista implements Serializable, Comparable<Turista> {

    //TODO sredi ovaj serial version
    private static final long serialVersionUID = 7526472295622776147L;
    private String ime;
    private int novac;
    private NacinKretanja kretanje;
    private int vrijemeKretanja;
    private File folder;
    private ArrayList<File> leci = new ArrayList<File>();
    private int brojPosjecenihTA;
    private String brojPosjecenihTAprocena;

    private int red, kolona;

    private static ArrayList<String> imenaSvihTurista = new ArrayList<>();

    public Turista() {
        Random rand = new Random();

        do {
            ime = "Turista(" + rand.nextInt(500) + ")";
        } while (Arrays.asList(imenaSvihTurista).contains(ime));

        novac = rand.nextInt(100);
        kretanje = NacinKretanja.values()[rand.nextInt(NacinKretanja.values().length)];
        

        vrijemeKretanja = 20;
        

        folder = new File("turisti" + File.separator + ime);
        this.folder.mkdir();
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getNovac() {
        return novac;
    }

    public void setNovac(int novac) {
        this.novac = novac;
    }

    public NacinKretanja getKretanje() {
        return kretanje;
    }

    public void setKretanje(NacinKretanja kretanje) {
        this.kretanje = kretanje;
    }

    public int getVrijemeKretanja() {
        return vrijemeKretanja;
    }

    public void setVrijemeKretanja(int vrijemeKretanja) {
        this.vrijemeKretanja = vrijemeKretanja;
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }

    public ArrayList<File> getLeci() {
        return leci;
    }

    public void setLeci(ArrayList<File> leci) {
        this.leci = leci;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getKolona() {
        return kolona;
    }

    public void setKolona(int kolona) {
        this.kolona = kolona;
    }

    public int getBrojPosjecenihTA() {
        return brojPosjecenihTA;
    }

    public void setBrojPosjecenihTA(int brojPosjecenihTA) {
        this.brojPosjecenihTA = brojPosjecenihTA;
    }

    @Override
    public int compareTo(Turista other) {
        //return Integer.compare(this.brojPosjecenihTA, other.brojPosjecenihTA);

        return this.brojPosjecenihTA - other.brojPosjecenihTA;

    }

    @Override
    public String toString() {
        return "Turista{" + "ime=" + ime + ", novac=" + novac + ", kretanje=" + kretanje + ", vrijemeKretanja=" + vrijemeKretanja + ", folder=" + folder + ", leci=" + leci + ", brojPosjecenihTA=" + brojPosjecenihTA + ", red=" + red + ", kolona=" + kolona + '}';
    }

    public String getBrojPosjecenihTAprocena() {
        return brojPosjecenihTAprocena;
    }

    public void setBrojPosjecenihTAprocena(String brojPosjecenihTAprocena) {
        this.brojPosjecenihTAprocena = brojPosjecenihTAprocena;
    }

    public static ArrayList<String> getImenaSvihTurista() {
        return imenaSvihTurista;
    }

    public static void setImenaSvihTurista(ArrayList<String> imenaSvihTurista) {
        Turista.imenaSvihTurista = imenaSvihTurista;
    }
    
    

}
