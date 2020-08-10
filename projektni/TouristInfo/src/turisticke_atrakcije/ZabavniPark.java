/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticke_atrakcije;

import java.util.Random;

/**
 *
 * @author Dell
 */
public class ZabavniPark extends TuristickaAtrakcija {
    private double cijena;

    public ZabavniPark(String naziv, String lokacija) {
        super(naziv, lokacija, "Zabavni park", NacinPlacanja.PLACANJE);
        this.cijena = new Random().nextInt(71) + 10;
    }
    
    public ZabavniPark(String naziv, String lokacija, double cijena) {
        super(naziv, lokacija, "Zabavni park", NacinPlacanja.PLACANJE);
        this.cijena = cijena;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }
    
    public void setCijena() {
        this.cijena = new Random().nextInt(71) + 10;
    }

    @Override
    public String toString() {
        return super.toString() + "     cijena: " + cijena;
    }
    
    
    
}
