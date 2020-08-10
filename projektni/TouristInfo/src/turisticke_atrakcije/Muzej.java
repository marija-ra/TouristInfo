/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticke_atrakcije;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class Muzej extends TuristickaAtrakcija {

    private File letak;
    private double cijena;

    
    public Muzej(String naziv, String lokacija, File letak, double cijena) {
        super(naziv, lokacija, "Muzej", NacinPlacanja.PLACANJE);
        this.letak = letak;
        this.cijena = cijena;

    }

    public Muzej(String naziv, String lokacija, File letak) {
        super(naziv, lokacija, "Muzej", NacinPlacanja.PLACANJE);
        this.letak = letak;
        this.cijena = new Random().nextInt(71) + 10;
        ;
    }

    public File getLetak() {
        return letak;
    }

    public void setLetak(File letak) {
        this.letak = letak;
    }

    public double getCijena() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("dan u sedmici Sunday(1) - Saturday(7)=  " + day);
        //Sunday(1) - Saturday(7)

        if (day % 2 != 0 && day != 1) //paran dan
        {
            return 0.0;
        } else {
            return cijena;
        }

    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }
    
    public void setCijena() {
        this.cijena = new Random().nextInt(71) + 10;
    }

    @Override
    public String toString() {

        return super.toString() + "     cijena: " + cijena + "     ime letka: " + letak.getName();
    }

}
