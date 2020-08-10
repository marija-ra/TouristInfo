/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticka_mapa;

import controllers.RootController;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.TextArea;
import serijalizacija.Serialization;
import turista.Turista;
import turisticke_atrakcije.TuristickaAtrakcija;

/**
 *
 * @author Dell
 */
public class Mapa {

    
    private int brojTurista;
    private int dimenzije;
    private int brojTuristickihObjekata;

    private Object[][] matrica;
    

    public Mapa(int dimenzije, int brojTuristickihObjekata) {

        this.brojTurista = RootController.turisti.size();
        
        
        this.dimenzije = dimenzije;
        this.brojTuristickihObjekata = brojTuristickihObjekata;

        matrica = new Object[dimenzije][dimenzije];
        for (int i = 0; i < dimenzije; i++) {
            for (int j = 0; j < dimenzije; j++) {
                matrica[i][j] = new String("*");
            }
        }

        //postaviti atrakcije
        popuniAtrakcijeUMapu();

        //postaviti turiste
        popuniTuristeUMapu();

    }

    public void popuniAtrakcijeUMapu() {

        ArrayList<TuristickaAtrakcija> turistickeAtrakcije = new ArrayList<TuristickaAtrakcija>();
        turistickeAtrakcije = Serialization.deserialize("mapa" + File.separator + RootController.TOUR_MAP);

        for (TuristickaAtrakcija ta : turistickeAtrakcije);

        Random rand = new Random();

        //brojatrakcija je manja od broja atrakcija
        boolean pozicijaZauzeta = false;
        int limit;
        boolean poduplajTA = false;

        System.out.println("brojTuristickihObjekata" + brojTuristickihObjekata);
        System.out.println("turistickeAtrakcije.size()" + turistickeAtrakcije.size());

        if (brojTuristickihObjekata <= turistickeAtrakcije.size()) {
            limit = brojTuristickihObjekata;
        } else {
            limit = turistickeAtrakcije.size();
            poduplajTA = true;
        }
        System.out.println("limit" + limit);

        for (int i = 0; i < limit; i++) {
            do {
                int red = rand.nextInt(dimenzije);
                int kolona = rand.nextInt(dimenzije);

                if ("*".equals(matrica[red][kolona])) {
                    matrica[red][kolona] = turistickeAtrakcije.get(i);
                    pozicijaZauzeta = false;
                } else {
                    pozicijaZauzeta = true;
                }
            } while (pozicijaZauzeta);

        }

        if (poduplajTA) {
            System.out.println("poduplajTA" + poduplajTA);
            for (int i = 0;
                    i < brojTuristickihObjekata - turistickeAtrakcije.size();
                    i++) {

                TuristickaAtrakcija ta = turistickeAtrakcije.get(rand.nextInt(turistickeAtrakcije.size()));
                do {
                    int red = rand.nextInt(dimenzije);
                    int kolona = rand.nextInt(dimenzije);

                    if ("*".equals(matrica[red][kolona])) {

                        matrica[red][kolona] = ta;
                        pozicijaZauzeta = false;

                    } else {
                        pozicijaZauzeta = true;
                    }
                } while (pozicijaZauzeta);

            }
        }

    }

    public void popuniTuristeUMapu() {
        Random rand = new Random();
        boolean pozicijaZauzeta = false;

        for (Turista t : RootController.turisti) {
            do {
                int red = rand.nextInt(dimenzije);
                int kolona = rand.nextInt(dimenzije);
                if (("*".equals(matrica[red][kolona]))) {
                    matrica[red][kolona] = t;
                    t.setRed(red);
                    t.setKolona(kolona);
                    pozicijaZauzeta = false;
                } else {
                    pozicijaZauzeta = true;
                }
            } while (pozicijaZauzeta);
        }

    }

    
    public void ispisMatrice() {
        for (int i = 0; i < dimenzije; i++) {
            for (int j = 0; j < dimenzije; j++) {
                if (matrica[i][j] instanceof TuristickaAtrakcija) {
                    System.out.print("TA  ");
                } else if (matrica[i][j] instanceof Turista) {

                    System.out.print("T   ");
                } else {
                    System.out.print("*   ");
                }
            }
            System.out.println("");
        }
    }

    public void resetMapu() {
        for (int i = 0; i < dimenzije; ++i) {
            for (int j = 0; j < dimenzije; ++j) {

                if (!(matrica[i][j] instanceof TuristickaAtrakcija)) {
                    matrica[i][j] = new String("*");
                }

            }
        }
    }

    public void resetTuriste() {
        for (Turista t : RootController.turisti) {
            if (!(RootController.mapa.getMatrica()[t.getRed()][t.getKolona()] instanceof TuristickaAtrakcija)) {
                System.out.println(t.getIme() + " red" + t.getRed()+ " kolona" + t.getKolona());
                RootController.mapa.getMatrica()[t.getRed()][t.getKolona()] = t;

                //RootController.mapa.resetTuriste(red, kolona);
            }
            else System.out.println("TURISTICKA ATRAKCIJA red: " + t.getRed() + "kolona " + t.getKolona());
        }
    }

    public void popuniTextArea(TextArea textArea) {
        String mapa = " \n";
        for (int i = 0; i < dimenzije; i++) {
            for (int j = 0; j < dimenzije; j++) {
                if (matrica[i][j] instanceof TuristickaAtrakcija || "TA".equals(matrica[i][j])) {
                    mapa += "TA   ";
                } else if (matrica[i][j] instanceof Turista || "T".equals(matrica[i][j])) {
                    mapa += "T     ";
                } else {
                    mapa += "*     ";
                }
            }
            mapa += "\n ";
        }
        
        textArea.setText(mapa);
        

    }

    public String stringTextArea() {
        String mapa = " \n";
        for (int i = 0; i < dimenzije; i++) {
            for (int j = 0; j < dimenzije; j++) {
                if (matrica[i][j] instanceof TuristickaAtrakcija || "TA".equals(matrica[i][j])) {
                    mapa += "TA   ";
                } else if (matrica[i][j] instanceof Turista || "T".equals(matrica[i][j])) {
                    mapa += "T     ";
                } else {
                    mapa += "*     ";
                }
            }
            mapa += "\n ";
        }

        return mapa;

    }

    
    public void osvjeziTuriste() {
        for (Turista t : RootController.turisti) {
            if (!(matrica[t.getRed()][t.getKolona()] instanceof TuristickaAtrakcija)) {
                matrica[t.getRed()][t.getKolona()] = t;
            }
        }
    }

    public int getBrojTurista() {
        return brojTurista;
    }

    public void setBrojTurista(int brojTurista) {
        this.brojTurista = brojTurista;
    }

    public int getDimenzije() {
        return dimenzije;
    }

    public void setDimenzije(int dimenzije) {
        this.dimenzije = dimenzije;
    }

    public int getBrojTuristickihObjekata() {
        return brojTuristickihObjekata;
    }

    public void setBrojTuristickihObjekata(int brojTuristickihObjekata) {
        this.brojTuristickihObjekata = brojTuristickihObjekata;
    }

    public Object[][] getMatrica() {
        return matrica;
    }

    public void setMatrica(Object[][] matrica) {
        this.matrica = matrica;
    }

}
