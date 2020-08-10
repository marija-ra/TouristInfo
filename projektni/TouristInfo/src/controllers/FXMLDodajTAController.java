/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import turisticke_atrakcije.Crkva;
import turisticke_atrakcije.IstorijskiSpomenik;
import turisticke_atrakcije.Muzej;
import turisticke_atrakcije.ZabavniPark;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLDodajTAController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private Button choosePhotoButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button chooseFlyerButton;

    @FXML
    private TextField costTextField;

    @FXML
    private ImageView ImageView;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button ConfirmButton;
    @FXML
    private Label fileNameLabel;

    @FXML
    private Label errorLabel;
    
    public boolean buttonChange = false;

    @FXML
    void ConfirmButtonAction(ActionEvent event) throws IOException {
        
        System.out.println("ROOT PHOTO CONFIRM" + RootController.photo);

        setErrorTextMessage();

        if ("".equals(errorLabel.getText())) {
            System.out.println("Confirm Button Action - error label : " + errorLabel.getText());

            if (RootController.edit == false) {
                createTA();
                RootController.photo = null;
                RootController.flyer = null;
            }
            if (RootController.edit == true) {
                editTA();
                //System.out.println(RootController.selectedTA);
            }

            RootController.edit = false;

            ((Stage) nameTextField.getScene().getWindow()).close();
            RootController.tableViewReference.refresh();

        } else {
            System.out.println("confirm button action - prazna error labela");
        }

    }

    public void setErrorTextMessage() {
        String errorMessage = "";
        Boolean error = false,
                costValid = true;

        int empty[];

        System.out.println("confirm button Action - pocetak");

        if (RootController.edit) {

            empty = areEditTextFieldsEmpty();
        } else {
            empty = areAddTextFieldsEmpty();
        }

        
        System.out.println("confirm button Action - prije e");
        for (int e : empty) {

            if (e == 1) {
                errorMessage += "Popuni sva polja.\n";
                error = true;
            }
            if (e == 2) {
                errorMessage += "Odaberi sliku.\n";
                error = true;
            }
            if (e == 3) {
                errorMessage += "Odaberi letak.\n";
                error = true;
            }
        }

        System.out.println("confirm button Action - prije provjere cijene");

        
        if ((RootController.edit == false && "Muzej".equals(RootController.selectedType))
                || (RootController.edit == true && RootController.selectedTA instanceof Muzej)) {

            if (!"".equals(costTextField.getText())) {
                costValid = RootController.isCostNumber(costTextField.getText());
                if (!costValid) {
                    errorMessage += "U polje za cijenu unesite broj.\n";
                }
            }
        }

        if ((RootController.edit == false && "Zabavni park".equals(RootController.selectedType))
                || (RootController.edit == true && RootController.selectedTA instanceof ZabavniPark)) {

            if (!"".equals(costTextField.getText())) {
                costValid = RootController.isCostNumber(costTextField.getText());
                if (!costValid) {
                    errorMessage += "U polje za cijenu unesite broj.\n";
                }
            }
            errorMessage += "Ukoliko ostavite prazno polje za cijenu,\ngenerisaće se slučajna vrijednost.\n";
        }

        if (error == true || costValid == false) {
            errorLabel.setText(errorMessage);

        } else {
            errorLabel.setText("");
        }
        System.out.println("confirm button Action - kraj");

    }

    @FXML
    void choosePhotoButtonAction(ActionEvent event) throws MalformedURLException, IOException {
        
        buttonChange= true;

     
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.jpg", "*jpeg", "*.JPG", "*JPEG");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File defaultFile = new File("\\.");       

        fileChooser.setInitialDirectory(defaultFile);
        fileChooser.setTitle("Odaberi sliku");
        
        File file = fileChooser.showOpenDialog((Stage) ConfirmButton.getScene().getWindow());

        if (file != null) {
            try {

                Image image = new Image(file.toURI().toURL().toString());
                System.out.println(" ***IMAGE* choose photo button " + file.toURI().toURL().toString());
                ImageView.setImage(image);
                RootController.photo = file;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setErrorTextMessage();
        

        System.out.println("choose photo metoda " + RootController.photo);

    }

    @FXML
    void chooseFlyerButtonAction(ActionEvent event
    ) {
        buttonChange= true;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt", "*txt", "*.TXT", "*.TXT");
        fileChooser.getExtensionFilters().add(extFilter);
        File defaultFile = new File("\\.");
        fileChooser.setInitialDirectory(defaultFile);
        fileChooser.setTitle("Choose Flyer");
        File file = fileChooser.showOpenDialog((Stage) nameTextField.getScene().getWindow());
        if (file != null) {
            fileNameLabel.setText("Flyer:  " + file.getName());
            System.out.println(file.getName());
            RootController.flyer = file;
        }
        
        setErrorTextMessage();
        

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        RootController.vBoxReference = vbox;

        if (RootController.edit) {
            editInitialize();
        } else {
            addInitialize();
        }

    }

    public void editInitialize() {

        nameTextField.setText(RootController.selectedTA.getNaziv());
        locationTextField.setText(RootController.selectedTA.getLokacija());

        if (RootController.selectedTA instanceof IstorijskiSpomenik) {
            descriptionTextArea.setText(((IstorijskiSpomenik) RootController.selectedTA).getOpis());
            try {
                String path = ((IstorijskiSpomenik) RootController.selectedTA).getPhoto().toURI().toURL().toString();
                String path2 = ((IstorijskiSpomenik) RootController.selectedTA).getPhoto().getAbsolutePath();

                Image photo = new Image(path);
                System.out.println(" ***IMAGE* edit initialize " + path);

                ImageView.setImage(photo);
                
                File file = new File(path2);
                RootController.photo = file;
                
                ((IstorijskiSpomenik) RootController.selectedTA).setPhoto(RootController.photo);
                

            } catch (Exception e) {
                e.printStackTrace();
            }

            vbox.getChildren().remove(6);
            vbox.getChildren().remove(5);
            vbox.getChildren().remove(2);

        } else if (RootController.selectedTA instanceof Muzej) {

            costTextField.setText(Double.toString(((Muzej) RootController.selectedTA).getCijena()));
            fileNameLabel.setText(((Muzej) RootController.selectedTA).getLetak().getName());
            RootController.flyer = ((Muzej) RootController.selectedTA).getLetak().getAbsoluteFile();
            if (((Muzej) RootController.selectedTA).getLetak() != null) {
                System.out.println("PUTANJA LETKA IZMJENA" + ((Muzej) RootController.selectedTA).getLetak().getAbsoluteFile());
            } else {
                System.out.println("nema letka");
            }

            vbox.getChildren().remove(7);
            vbox.getChildren().remove(4);
            vbox.getChildren().remove(3);

        } else if (RootController.selectedTA instanceof ZabavniPark) {

            costTextField.setText(Double.toString(((ZabavniPark) RootController.selectedTA).getCijena()));

            vbox.getChildren().remove(7);
            vbox.getChildren().remove(6);
            vbox.getChildren().remove(5);
            vbox.getChildren().remove(4);
            vbox.getChildren().remove(3);

        } else if (RootController.selectedTA instanceof Crkva) {
            vbox.getChildren().remove(7);
            vbox.getChildren().remove(6);
            vbox.getChildren().remove(5);
            vbox.getChildren().remove(4);
            vbox.getChildren().remove(3);
            vbox.getChildren().remove(2);
        }
    }

    public void addInitialize() {
        switch (RootController.selectedType) {

            case "Istorijski spomenik":
                vbox.getChildren().remove(6);
                vbox.getChildren().remove(5);
                vbox.getChildren().remove(2);
                break;
            case "Muzej":
                vbox.getChildren().remove(7);
                vbox.getChildren().remove(4);
                vbox.getChildren().remove(3);
                break;
            case "Zabavni park":
                vbox.getChildren().remove(7);
                vbox.getChildren().remove(6);
                vbox.getChildren().remove(5);
                vbox.getChildren().remove(4);
                vbox.getChildren().remove(3);
                break;
            case "Crkva":
                vbox.getChildren().remove(7);
                vbox.getChildren().remove(6);
                vbox.getChildren().remove(5);
                vbox.getChildren().remove(4);
                vbox.getChildren().remove(3);
                vbox.getChildren().remove(2);
                break;

        }
    }

    /* 
    temp[0] = 0  > nisu prazna
    temp[0] = 1  > jesu prazna
    temp[1] = 2  > nema slike
    temp[2] = 3  > nema letka    
     */
    
   
    
    public int[] areAddTextFieldsEmpty() {

        int temp[] = new int[3];
        temp[0] = 0;
        temp[1] = 0;
        temp[2] = 0;

        System.out.println(RootController.selectedType);

        //crkva
        if ("".equals(nameTextField.getText()) || "".equals(locationTextField.getText())) {
            temp[0] = 1;
        }
        //istorijski spomenik 
        if ("Istorijski spomenik".equals(RootController.selectedType) && "".equals(descriptionTextArea.getText())) {
            temp[0] = 1;

        }

        if ("Istorijski spomenik".equals(RootController.selectedType) && RootController.photo == null && RootController.edit == false) {
            temp[1] = 2;

        } //Muzej
        if ("Muzej".equals(RootController.selectedType) && RootController.flyer == null && RootController.edit == false) {
            temp[2] = 3;
        }

        if ("Muzej".equals(RootController.selectedType) && "".equals(costTextField.getText())) {
            //temp[0] = 1;
        } //Zabavni park

        if ("Zabavni park".equals(RootController.selectedType) && "".equals(costTextField.getText())) {
            //temp[0] = 1;
        }

        
        for (int i : temp) {
            System.out.println("u metodi " + i);
        }

        return temp;
    }

    public int[] areEditTextFieldsEmpty() {

        int temp[] = new int[3];
        temp[0] = 0;
        temp[1] = 0;
        temp[2] = 0;

        System.out.println(RootController.selectedTA);

        //crkva
        if ("".equals(nameTextField.getText()) || "".equals(locationTextField.getText())) {
            temp[0] = 1;
        }
        //istorijski spomenik 
        if (RootController.selectedTA instanceof IstorijskiSpomenik && "".equals(descriptionTextArea.getText())) {
            temp[0] = 1;

        }

        if (RootController.selectedTA instanceof IstorijskiSpomenik && RootController.photo == null && RootController.edit == false) {
            temp[1] = 2;

        } //Muzej
        if (RootController.selectedTA instanceof Muzej && RootController.flyer == null && RootController.edit == false) {
            temp[2] = 3;
        }

        if (RootController.selectedTA instanceof Muzej && "".equals(costTextField.getText())) {
            //temp[0] = 1;
        } //Zabavni park

        if (RootController.selectedTA instanceof ZabavniPark && "".equals(costTextField.getText())) {
            ////temp[0] = 1;
        }

        System.out.println(RootController.photo);
        System.out.println(RootController.flyer);
        for (int i : temp) {
            System.out.println("u metodi " + i);
        }

        return temp;
    }

    public void createTA() throws IOException {

        switch (RootController.selectedType) {

            case "Istorijski spomenik": {
                try {
                    File slika = new File("slike" + File.separator + RootController.photo.getName());
                    
                    
                    RootController.copyFile(RootController.photo, slika);
                    
                    RootController.TAObservableList.add(new IstorijskiSpomenik(nameTextField.getText(), locationTextField.getText(), descriptionTextArea.getText(), RootController.photo));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDodajTAController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            RootController.photo = null;
            break;
            case "Muzej":
                File letak = new File("leci" + File.separator + RootController.flyer.getName());
                RootController.copyFile(RootController.flyer, letak);
                RootController.flyer = letak;
                if ("".equals(costTextField.getText())) {
                    RootController.TAObservableList.add(new Muzej(nameTextField.getText(), locationTextField.getText(), RootController.flyer));
                } else {
                    RootController.TAObservableList.add(new Muzej(nameTextField.getText(), locationTextField.getText(), RootController.flyer, Double.parseDouble(costTextField.getText())));
                }

                RootController.flyer = null;
                break;
            case "Zabavni park":
                if ("".equals(costTextField.getText())) {
                    RootController.TAObservableList.add(new ZabavniPark(nameTextField.getText(), locationTextField.getText()));
                } else {
                    RootController.TAObservableList.add(new ZabavniPark(nameTextField.getText(), locationTextField.getText(), Double.parseDouble(costTextField.getText())));
                }
                break;

            case "Crkva":
                RootController.TAObservableList.add(new Crkva(nameTextField.getText(), locationTextField.getText()));
                break;
        }
    }

    public void editTA() throws IOException {

        RootController.selectedTA.setNaziv(nameTextField.getText());
        RootController.selectedTA.setLokacija(locationTextField.getText());

        if (RootController.selectedTA instanceof IstorijskiSpomenik) {
            
            
            System.out.println("buttonChange"+buttonChange);
            
            ((IstorijskiSpomenik) RootController.selectedTA).setOpis(descriptionTextArea.getText());
            ((IstorijskiSpomenik) RootController.selectedTA).setPhoto(RootController.photo);

            

            
            File slika = new File("slike" + File.separator + RootController.photo.getName());
            

            
            
            if(buttonChange) RootController.copyFile(RootController.photo, slika);
            
            

            RootController.photo = null;
            
        }

        if (RootController.selectedTA instanceof Muzej) {
            System.out.println("buttonChange"+buttonChange);
            File letak = new File("leci" + File.separator + RootController.flyer.getName());
            if(buttonChange)RootController.copyFile(RootController.flyer, letak);
            RootController.flyer = letak;

            if ("".equals(costTextField.getText())) {
                ((Muzej) RootController.selectedTA).setCijena();
            } else {
                ((Muzej) RootController.selectedTA).setCijena(Double.parseDouble(costTextField.getText()));
            }

            ((Muzej) RootController.selectedTA).setLetak(RootController.flyer);
            RootController.flyer = null;

        }
        if (RootController.selectedTA instanceof ZabavniPark) {

            if ("".equals(costTextField.getText())) {
                ((ZabavniPark) RootController.selectedTA).setCijena();
            } else {
                ((ZabavniPark) RootController.selectedTA).setCijena(Double.parseDouble(costTextField.getText()));
            }
        }

    }

}
