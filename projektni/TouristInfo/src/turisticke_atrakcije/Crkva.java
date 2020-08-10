/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticke_atrakcije;

/**
 *
 * @author Dell
 */
public class Crkva extends TuristickaAtrakcija{
    
    private double donacije;
    
    
    public Crkva(String naziv, String lokacija) {
        super(naziv, lokacija, "Crkva", NacinPlacanja.DONACIJA);
        
    }

    public double getDonacije() {
        return donacije;
    }

    public void setDonacije(double donacije) {
        this.donacije = donacije;
    }

    @Override
    public String toString() {
        return super.toString() + "     donacije: " + donacije;
    }
    
    
   
    
    
    
}
