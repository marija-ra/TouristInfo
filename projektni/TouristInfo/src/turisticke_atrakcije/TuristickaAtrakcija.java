/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticke_atrakcije;

import java.io.Serializable;

/**
 *
 * @author Dell
 */
public class TuristickaAtrakcija implements Serializable {
    
    private String naziv;
    private String lokacija;
    private String tipAtrakcije;
    private NacinPlacanja placanje;

    public TuristickaAtrakcija(String naziv, String lokacija, String tipAtrakcije, NacinPlacanja placanje) {
        this.naziv = naziv;
        this.lokacija = lokacija;
        this.tipAtrakcije = tipAtrakcije;
        this.placanje = placanje;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getLokacija() {
        return lokacija;
    }

    public String getTipAtrakcije() {
        return tipAtrakcije;
    }

    public NacinPlacanja getPlacanje() {
        return placanje;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public void setTipAtrakcije(String tipAtrakcije) {
        this.tipAtrakcije = tipAtrakcije;
    }

    public void setPlacanje(NacinPlacanja placanje) {
        this.placanje = placanje;
    }

    @Override
    public String toString() {
        return tipAtrakcije + " >    naziv: " + naziv + "     lokacija: " + lokacija ;
        
    }
    
    
    
    
}
