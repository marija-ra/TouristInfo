/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turista;

import analizator_rezultata.AnalizatorRezultataServer;
import arhiver.Arhiver;

import controllers.RootController;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import serijalizacija.Serialization;
import turisticka_mapa.Slika;
import turisticke_atrakcije.Crkva;
import turisticke_atrakcije.IstorijskiSpomenik;
import turisticke_atrakcije.Muzej;
import turisticke_atrakcije.TuristickaAtrakcija;
import turisticke_atrakcije.ZabavniPark;

/**
 *
 * @author Dell
 */
public class Turista extends Thread implements Serializable {

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
            ime = "Turista     (" + rand.nextInt(500) + ")";
        } while (Arrays.asList(imenaSvihTurista).contains(ime));

        novac = rand.nextInt(100);
        kretanje = NacinKretanja.values()[rand.nextInt(NacinKretanja.values().length)];

        vrijemeKretanja = rand.nextInt(6000) + 1000;

        folder = new File("turisti" + File.separator + ime);
        this.folder.mkdir();
    }

    public void run() {
        this.brojPosjecenihTAprocena = "";
        int naredniRed, narednaKolona;
        int dimenzije = RootController.mapa.getDimenzije();
        int counter = dimenzije;

        switch (kretanje) {

            case SAMO_U_JEDNOM_REDU:
                ////////////////////////////
                while (counter > 0) {

                    counter--;

                    narednaKolona = (kolona + 1) % dimenzije;

                    try {
                        this.sleep(vrijemeKretanja);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (RootController.mapa.getMatrica()[red][narednaKolona] instanceof TuristickaAtrakcija) {

                        if (!this.posjetiTA((TuristickaAtrakcija) RootController.mapa.getMatrica()[red][narednaKolona], red, narednaKolona)) {

                            counter = 0;
                            break;
                        }
                    }

                    kolona++;
                    kolona = kolona % dimenzije;

                    RootController.mapa.resetMapu();
                    RootController.mapa.resetTuriste();

                    TextArea textArea = (TextArea) RootController.takmicenje.getScene().lookup("#gameTextArea");

                    RootController.mapa.popuniTextArea(textArea);

                }
                break;

            case DIJAGONALNO:
                ////////////////////////////
                int pocetakRed = red;
                int pocetakKolona = kolona;

                do {

                    naredniRed = red + 1;
                    narednaKolona = kolona + 1;

                    if (naredniRed == dimenzije) {
                        naredniRed = naredniRed - narednaKolona;
                        narednaKolona = 0;
                    } else if (narednaKolona == dimenzije) {

                        narednaKolona = narednaKolona - naredniRed;
                        naredniRed = 0;
                    }

                    try {
                        this.sleep(vrijemeKretanja);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (RootController.mapa.getMatrica()[naredniRed][narednaKolona] instanceof TuristickaAtrakcija) {

                        if (!this.posjetiTA((TuristickaAtrakcija) RootController.mapa.getMatrica()[naredniRed][narednaKolona], naredniRed, narednaKolona)) {

                            counter = 0;
                            break;
                        }
                    }

                    kolona = narednaKolona;
                    red = naredniRed;

                    RootController.mapa.resetMapu();
                    RootController.mapa.resetTuriste();

                    TextArea textArea = (TextArea) RootController.takmicenje.getScene().lookup("#gameTextArea");
                    RootController.mapa.popuniTextArea(textArea);

                } while ((!(pocetakRed == naredniRed) && !(pocetakKolona == narednaKolona)) || counter == 0);

                break;

            case KROZ_CIJELU_MATRICU:
                counter = (int) Math.pow(dimenzije, 2);

                while (counter > 0) {

                    counter--;

                    naredniRed = red;
                    narednaKolona = (kolona + 1) % dimenzije;

                    if (narednaKolona == 0) {
                        naredniRed = (red + 1) % dimenzije;
                    }

                    try {
                        this.sleep(vrijemeKretanja);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (RootController.mapa.getMatrica()[naredniRed][narednaKolona] instanceof TuristickaAtrakcija) {

                        if (!this.posjetiTA((TuristickaAtrakcija) RootController.mapa.getMatrica()[naredniRed][narednaKolona], naredniRed, narednaKolona)) {
                            System.out.println("NEMA PARA");
                            counter = 0;
                            break;
                        }
                    }

                    kolona = narednaKolona;
                    red = naredniRed;

                    RootController.mapa.resetMapu();
                    RootController.mapa.resetTuriste();

                    TextArea textArea = (TextArea) RootController.takmicenje.getScene().lookup("#gameTextArea");

                    RootController.mapa.popuniTextArea(textArea);

                }
                break;

        }

        this.procentiTA();

        System.out.println(this.ime + " Obisao: " + brojPosjecenihTAprocena + " broj posjecenih i ukupnih mjesta: " + RootController.mapa.getBrojTuristickihObjekata() + " + RootController.brojturista +  " + brojPosjecenihTA);
        boolean daLiJePoslednji = RootController.daLiJePoslednjiTuristaUIgri();

        TextArea informacijeTextArea = (TextArea) RootController.takmicenje.getScene().lookup("#logTextArea");
        if (daLiJePoslednji) {

            informacijeTextArea.appendText("GOTOVO\n");
            RootController.igraJeGotova = true;

            Button buttonResult = (Button) RootController.takmicenje.getScene().lookup("#resultsButton");
            buttonResult.setDisable(false);
            Button buttonRun = (Button) RootController.takmicenje.getScene().lookup("#runButton");
            buttonRun.setDisable(true);

            System.out.print("------------------");

            for (Turista t : RootController.turisti) {
                System.out.println(t);
            }

            System.out.print("------------------");

            String fileName = "turisti.ser";
            Serialization.serialize(RootController.turisti, "ser", fileName);

            //Analizator rezultata
            AnalizatorRezultataServer rezultati = new AnalizatorRezultataServer();
            RootController.rezultati = rezultati;
            rezultati.setFile(new File("ser" + File.separator + fileName));
            rezultati.start();

            //Arhiver rezultata
            Arhiver arhiver = new Arhiver();
            arhiver.start();

            try {
                rezultati.join();
                arhiver.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean posjetiTA(TuristickaAtrakcija turistickaAtrakcija, int red, int kolona) {

        System.out.println("posjeti TA1");

        Random rand = new Random();
        TextArea logTextArea = (TextArea) RootController.takmicenje.getScene().lookup("#logTextArea");
        if (turistickaAtrakcija instanceof IstorijskiSpomenik) {
            System.out.println(this.ime + " || posjetio istorijski spomenik|| ");

            IstorijskiSpomenik istorijskiSpomenik = (IstorijskiSpomenik) turistickaAtrakcija;

            new Thread(() -> {
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    new Slika(istorijskiSpomenik.getPhoto(), stage).start();
                });
            }).start();

            logTextArea.appendText(this.ime);
            logTextArea.appendText("\n--------------------------------------------------------------------------------\n");

            logTextArea.appendText("[ red: " + red + ",  kolona: " + kolona + "]\n");

            logTextArea.appendText("turista je posjetio ISTORIJSKI SPOMENIK\n");
            logTextArea.appendText("--------------------------------------------------------------------------------\n\n\n");
        }
        if (turistickaAtrakcija instanceof Muzej) {
            System.out.println(this.ime + " || posjetio muzej|| ");

            Muzej muzej = (Muzej) turistickaAtrakcija;

            double cijenaKarte = muzej.getCijena();

            logTextArea.appendText(this.ime);
            logTextArea.appendText("\n--------------------------------------------------------------------------------\n");

            logTextArea.appendText("[ red: " + red + ",  kolona: " + kolona + "]\n");

            if (plati(cijenaKarte)) {
                logTextArea.appendText("turista je posjetio MUZEJ\n");
                InputStream is = null;
                try {
                    is = new FileInputStream(muzej.getLetak());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                try {
                    
                    File target = new File(folder.getAbsolutePath() + File.separator + muzej.getLetak().getName().split("\\.")[0] + ".txt");
                    this.leci.add(target);
                    Files.copy(is, target.toPath(), REPLACE_EXISTING);
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                logTextArea.appendText("nema dovoljno novca kako bi posjetio ZABAVNI PARK.\n");
            }
            logTextArea.appendText("--------------------------------------------------------------------------------\n\n\n");

        }
        if (turistickaAtrakcija instanceof ZabavniPark) {
            System.out.println(this.ime + " || posjetio zabavni park|| ");

            ZabavniPark zabavniPark = (ZabavniPark) turistickaAtrakcija;

            double cijenaKarte = zabavniPark.getCijena();
            logTextArea.appendText(this.ime);
            logTextArea.appendText("\n--------------------------------------------------------------------------------\n");

            logTextArea.appendText("[ red: " + red + ",  kolona: " + kolona + "]\n");

            if (plati(cijenaKarte)) {
                logTextArea.appendText("turista je posjetio ZABAVNI PARK\n");
            } else {
                logTextArea.appendText("nema dovoljno novca kako bi posjetio ZABAVNI PARK.\n");
            }
            logTextArea.appendText("--------------------------------------------------------------------------------\n\n\n");

        }
        if (turistickaAtrakcija instanceof Crkva) {

            System.out.println(this.ime + " || posjetio crkvu|| ");

            Crkva crkva = (Crkva) turistickaAtrakcija;

            int donacija = rand.nextInt(50);
            logTextArea.appendText(this.ime);
            logTextArea.appendText("\n--------------------------------------------------------------------------------\n");

            logTextArea.appendText("[ red: " + red + ",  kolona: " + kolona + "]\n");
            if (plati(donacija)) {
                crkva.setDonacije(donacija + crkva.getDonacije());
                logTextArea.appendText("turista je posjetio CRKVU\n");

            } else {
                logTextArea.appendText("nema dovoljno novca kako bi posjetio CRKVU.\n");
            }
            logTextArea.appendText("--------------------------------------------------------------------------------\n\n\n");
        }

        ++this.brojPosjecenihTA;

        System.out.println("brojPosjecenihTA " + brojPosjecenihTA);
        return true;

    }

    public void procentiTA() {
        double x = (brojPosjecenihTA * 100.0);
        double y = x / RootController.mapa.getBrojTuristickihObjekata();

        this.brojPosjecenihTAprocena = "" + Math.round(y) + "%";

    }

    public boolean plati(double novac) {
        TextArea logTextArea = (TextArea) RootController.takmicenje.getScene().lookup("#logTextArea");
        logTextArea.appendText("u novcaniku ima : " + this.novac + ", a treba da plati : " + novac + "\n");
        int n = this.novac;
        n -= novac;

        if (n > 0) {
            this.novac = n;
            return true;
        } else {
            return false;
        }

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

    @Override
    public String toString() {
        return "Turista{" + "ime=" + ime + ", novac=" + novac + ", kretanje=" + kretanje + ", vrijemeKretanja=" + vrijemeKretanja + ", folder=" + folder + ", leci=" + leci + ", brojPosjecenihTA=" + brojPosjecenihTA + ", red=" + red + ", kolona=" + kolona + ", prcenat : " + brojPosjecenihTAprocena + '}';
    }

    public int getBrojPosjecenihTA() {
        return brojPosjecenihTA;
    }

    public void setBrojPosjecenihTA(int brojPosjecenihTA) {
        this.brojPosjecenihTA = brojPosjecenihTA;
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
