/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import turista.Turista;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLTakmicenjeController implements Initializable {
    
    @FXML
    private VBox vboxTakmicenje;

    @FXML
    private TextArea logTextArea;
    
    @FXML
    private Label gameStatus;


    @FXML
    private TextArea gameTextArea;

    @FXML
    private Button runButton;

    @FXML
    private Button resultsButton;

    @FXML
    void runButtonAction(ActionEvent event) {
        
        runButton.setDisable(true);

        logTextArea.clear();
        logTextArea.setText("");
        
        RootController.takmicenje = (Stage) gameTextArea.getScene().getWindow();
        for (Turista turista : RootController.turisti) {
            logTextArea.appendText("\n" + turista);
            turista.start();
        }

        logTextArea.appendText("\n");

    }

    @FXML
    void resultsButtonAction(ActionEvent event) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLRezultati.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            stage.setTitle("TouristInfo");
            ((Stage) gameTextArea.getScene().getWindow()).hide();
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        gameTextArea.clear();
        logTextArea.clear();
        gameTextArea.setEditable(false);
        logTextArea.setEditable(false);

        String s = RootController.takmicenjeTextArea;
        gameTextArea.setText(s);

        System.out.print(gameTextArea.getText());
        resultsButton.setDisable(true);
    }

}
