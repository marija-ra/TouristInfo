/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import turista.Turista;
import turisticka_mapa.Mapa;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLKorisnikController implements Initializable {

    @FXML
    private TextField brojTuristaTextField;

    @FXML
    private TextField dimenzijeMatriceTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField brojTuristickihObjekataTextField;

    @FXML
    void backButton(ActionEvent event) throws IOException {
        Stage stage = RootController.admin;
        ((Stage) dimenzijeMatriceTextField.getScene().getWindow()).hide();
        stage.show();

    }

    @FXML
    void confirmButtonAction(ActionEvent event) throws IOException {
        if (validTextField()) {
            
            RootController.brojturista = 0;

            File folderTuristi = new File("turisti");
            for (File f : folderTuristi.listFiles()) {

                for (File file : f.listFiles()) {
                    if (!file.isDirectory()) {
                        System.out.println("" + f + "  *  " + file);
                        file.delete();
                    }
                }
                System.out.println("DELETE: " + f);
                f.delete();

            }

            int brojTurista = Integer.parseInt(brojTuristaTextField.getText());
            int dimenzijeMatrice = Integer.parseInt(dimenzijeMatriceTextField.getText());
            int brojTuristickihObjekata = Integer.parseInt(brojTuristickihObjekataTextField.getText());
            
            brojTuristaTextField.clear();
            dimenzijeMatriceTextField.clear();
            brojTuristickihObjekataTextField.clear();
            
            

            ArrayList<Turista> turisti = new ArrayList<Turista>();
            for (int i = 0; i < brojTurista; i++) {
                turisti.add(new Turista());

            }

            RootController.turisti = turisti;

            System.out.print("dimenzijeMatrice " + dimenzijeMatrice);
            System.out.print("brojTuristickihObjekata " + brojTuristickihObjekata);
            RootController.mapa = new Mapa(dimenzijeMatrice, brojTuristickihObjekata);

            RootController.takmicenjeTextArea = RootController.mapa.stringTextArea();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLTakmicenje.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("TouristInfo");
            stage.show();

        }

    }

    public boolean validTextField() {

        boolean emptyField = false;
        String error = "";
        String spaceError = "";
        boolean valid = true;

        if ("".equals(brojTuristaTextField.getText())
                || "".equals(dimenzijeMatriceTextField.getText())
                || "".equals(brojTuristickihObjekataTextField.getText())) {
            valid = false;

            errorLabel.setText("Popunite sva polja");
        }

        if (valid
                && (!RootController.isNumber(brojTuristaTextField.getText())
                || !RootController.isNumber(dimenzijeMatriceTextField.getText())
                || !RootController.isNumber(brojTuristickihObjekataTextField.getText()))) {
            errorLabel.setText("Unesite samo brojeve u polja");
            valid = false;

        } else if (valid) {
            if ((Integer.parseInt(brojTuristaTextField.getText()) < 1)) {
                error += "Broj turista treba biti veći od 0\n";

            }
            if ((Integer.parseInt(dimenzijeMatriceTextField.getText()) < 3)) {
                error += "Dimenzije matrice trebaju bit veće od 2\n";
            }
            if ((Integer.parseInt(brojTuristickihObjekataTextField.getText()) < 1)) {
                error += "Broj turističkih objekata treba biti veći od 0\n";
            }

            if ("".equals(error)) {
                int ukupnoPozicija = Integer.parseInt(dimenzijeMatriceTextField.getText());

                ukupnoPozicija *= ukupnoPozicija;

                int zauzetePozicije
                        = Integer.parseInt(brojTuristaTextField.getText())
                        + Integer.parseInt(brojTuristickihObjekataTextField.getText());

                if (ukupnoPozicija < zauzetePozicije) {
                    spaceError = " Dimenzije matrice ne odgovaraju broju \nturista i turističkih objekata\n";
                }

            }

            errorLabel.setText(error + spaceError);
            valid = "".equals(error + spaceError);
        }

        return valid;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
