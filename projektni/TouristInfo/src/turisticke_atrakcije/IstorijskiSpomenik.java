/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticke_atrakcije;

import controllers.RootController;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Dell
 */
public class IstorijskiSpomenik extends TuristickaAtrakcija{
    
    private String opis;
    private File photo;

    public IstorijskiSpomenik(String naziv, String lokacija, String opis, File photo) throws IOException {
        super(naziv, lokacija, "Istorijski spomenik", NacinPlacanja.BESPLATNO);
        this.opis = opis;
        this.photo = photo;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public String getOpis() {
        return opis;
    }

    public File getPhoto() {
        return photo;
    }
    
    @Override
    public String toString() {
        return super.toString() + "     opis: " + opis + "      ime fajla: " + photo.getName(); 
    }
    
    
    
}
