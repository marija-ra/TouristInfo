/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import analizator_rezultata.Rezultati;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import turista.Turista;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLRezultatiController implements Initializable {

    @FXML
    private Button downloadCSVButton;

    @FXML
    private TableColumn<Turista, String> turistaTableColumn;

    @FXML
    private Button flyerButton;

    @FXML
    private TableView<Turista> top5TableView;

    @FXML
    private Label errorLabel;
    
    @FXML
    private Label arhiverInfoLabel;
    
    @FXML
    private Label csvLabel;

    @FXML
    private TableColumn<Turista, String> posjeceneAtrakcijeTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (RootController.topTuristi != null) {
            RootController.topTuristi.clear();
        }
        
        for (Turista t : RootController.turistiTop5) {
            RootController.topTuristi.add(t);
            
            System.out.println("top turista "+ t);
        }
        
        

        top5TableView.setItems(RootController.topTuristi);

        turistaTableColumn.setCellValueFactory(
                new PropertyValueFactory<Turista, String>("ime")
        );
        posjeceneAtrakcijeTableColumn.setCellValueFactory(
                new PropertyValueFactory<Turista, String>("brojPosjecenihTAprocena")
        );

        top5TableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Turista>() {
            @Override
            public void changed(ObservableValue<? extends Turista> observable, Turista oldValue, Turista newValue) {
                errorLabel.setText("");
                csvLabel.setText("");

                RootController.selektovaniTurista = newValue;
                System.out.println(RootController.selektovaniTurista);

            }

        });

        
        
        String prikaz = "";
        String[] pojedinacniRezultati = RootController.arhiverRezultati.split("#");
        for (String s : pojedinacniRezultati) {
            
            if (!s.equals("") && s != null) {
                String linijaPrikaz = s.split(":")[0] + "\t\t\t\t\t\t: " + s.split(":")[1];
                prikaz += linijaPrikaz + "\n";
            }
        }
        arhiverInfoLabel.setText(prikaz);


        Platform.runLater(() -> arhiverInfoLabel.requestFocus());

    
         
    }

    @FXML
    void downloadCSVButtonAction(ActionEvent event) {
        errorLabel.setText("");
        csvLabel.setText("");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog((Stage) downloadCSVButton.getScene().getWindow());

        if (selectedDirectory == null) {
            System.out.println("Odaberite lokaciju..");
        } else {
            try {
                RootController.rezultati.getOut().println("rezultati");
                Rezultati.receiveFile(new File(selectedDirectory.getAbsolutePath() + File.separator + "rezultati.csv"),
                        RootController.rezultati.getIn(), RootController.rezultati.getOut());
                csvLabel.setText("uspjesno preuzimanje");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void flyerButtonAction(ActionEvent event) {
        errorLabel.setText("");
        csvLabel.setText("");
        RootController.letakSadrzaj = "";
        File letak = null;
        if (RootController.selektovaniTurista == null) {
            errorLabel.setText("Odaberite turistu");
        } else {
        
        
        if (RootController.selektovaniTurista.getLeci().size() == 1) {
            letak = RootController.selektovaniTurista.getLeci().get(0);
            
        } else if (RootController.selektovaniTurista.getLeci().size() > 1) {
            Random rand = new Random();
            int index = rand.nextInt(RootController.selektovaniTurista.getLeci().size());
            letak = RootController.selektovaniTurista.getLeci().get(index);
        }
        
        

        if (letak != null) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(letak));//letak
                String line = "";
                while ((line = in.readLine()) != null) {
                    RootController.letakSadrzaj += line + "\n";
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLLetak.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }else errorLabel.setText("Turista nema letak");
        
        
    }
}
}
