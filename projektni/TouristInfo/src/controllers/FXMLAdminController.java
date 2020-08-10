/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import serijalizacija.Serialization;
import turisticke_atrakcije.TuristickaAtrakcija;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FXMLAdminController implements Initializable {

    //TODO : putanja 
   
    public ObservableList<String> tipoviComboBox = FXCollections.observableArrayList(
            "Istorijski spomenik",
            "Muzej",
            "Zabavni park",
            "Crkva"
    );

    @FXML
    private Button deleteButton;

    @FXML
    private Button startButton;

    @FXML
    private Button addButton;

    @FXML
    private Button changeButton;

    @FXML
    private Label errorLabel;

    @FXML
    void addButtonAction(ActionEvent event) throws IOException {
        RootController.edit = false;
        //RootController.photo = null;
       // RootController.flyer = null;

        if ("".equals(RootController.selectedType)) {
            errorLabel.setText("Odaberite tip turističke atrakcije");
        } else {

            errorLabel.setText("");

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLDodajTA.fxml"));
            System.out.println("POSLIJE");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            System.out.println(RootController.vBoxReference.getWidth());
            System.out.println(RootController.vBoxReference.getHeight());

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());
            stage.show();

            RootController.vBoxReference.requestFocus();
            stage.setWidth(RootController.vBoxReference.getWidth() + 50);
            stage.setHeight(RootController.vBoxReference.getHeight() + 100);
        }

    }

    @FXML
    void startButtonAction(ActionEvent event) {
//        File folderTuristi = new File("turisti");
//            for (File f : folderTuristi.listFiles()) {
//
//                for (File file : f.listFiles()) {
//                    if (!file.isDirectory()) {
//                        System.out.println(""+ f + "  *  " + file);
//                        file.delete();
//                    }
//                }
//                System.out.println("DELETE: " + f);
//                f.delete();
//
//            }
        
        RootController.admin = (Stage) deleteButton.getScene().getWindow();

        try {
            ArrayList<TuristickaAtrakcija> lista = new ArrayList();
            for (TuristickaAtrakcija atrakcija : RootController.TAObservableList) {
                
                lista.add(atrakcija);
            }

            //serialize
            Serialization.serialize(lista, "mapa", RootController.TOUR_MAP);

            //deserialize
            ArrayList<TuristickaAtrakcija> turistickeAtrakcijeLista = new ArrayList<TuristickaAtrakcija>();
            String turistickeAtrakcije = "";
            turistickeAtrakcijeLista = Serialization.deserialize("mapa" + File.separator + RootController.TOUR_MAP);

            
            if (turistickeAtrakcijeLista.isEmpty()) {
                errorLabel.setText("Dodajte atrakcije");
            } else {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLKorisnik.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("TouristInfo");
                ((Stage) addButton.getScene().getWindow()).hide();
                stage.show();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void changeButtonAction(ActionEvent event) {
        
        System.out.println("ROOT PHOTO CHANGE" + RootController.photo);
        RootController.edit = true;

        if (RootController.selectedTA == null) {
            errorLabel.setText("Odaberite turističku atrakciju koju želite da izmjenite");
        } else {

            errorLabel.setText("");

            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLDodajTA.fxml"));
                System.out.println("EDIT");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                System.out.println(RootController.vBoxReference.getWidth());
                System.out.println(RootController.vBoxReference.getHeight());

                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node) event.getSource()).getScene().getWindow());
                stage.show();

                RootController.vBoxReference.requestFocus();
                stage.setWidth(RootController.vBoxReference.getWidth() + 50);
                stage.setHeight(RootController.vBoxReference.getHeight() + 70);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void deleteButtonAction(ActionEvent event) {
        if (RootController.selectedTA != null) {
            RootController.TAObservableList.remove(RootController.selectedTA);
        } else {
            errorLabel.setText("označite atrakciju koju želite da obrišete.");
        }

    }
    @FXML
    private TableColumn<TuristickaAtrakcija, String> firstTableColumn;

    @FXML
    private TableColumn<TuristickaAtrakcija, String> secondTableColumn;

    @FXML
    private TableColumn<TuristickaAtrakcija, String> thirdTableColumn;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TableView<TuristickaAtrakcija> turistAttractionTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

                
        
        RootController.tableViewReference = turistAttractionTableView;

        combobox.setItems(tipoviComboBox);
        combobox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                RootController.selectedType = newValue;
                System.out.println(RootController.selectedType);
            }
        });

        firstTableColumn.setCellValueFactory(new PropertyValueFactory<TuristickaAtrakcija, String>("tipAtrakcije"));
        secondTableColumn.setCellValueFactory(new PropertyValueFactory<TuristickaAtrakcija, String>("naziv"));
        thirdTableColumn.setCellValueFactory(new PropertyValueFactory<TuristickaAtrakcija, String>("lokacija"));

        turistAttractionTableView.setItems(RootController.TAObservableList);
        
        if ((new File("mapa").list().length) > 0) {
            ArrayList<TuristickaAtrakcija> lista = Serialization.deserialize("mapa" + File.separator + "turisticka-mapa.ser");
            for (TuristickaAtrakcija atrakcija : lista) {
                RootController.TAObservableList.add(atrakcija);
                
                RootController.tableViewReference.refresh();
                        
            }
        }

        turistAttractionTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TuristickaAtrakcija>() {

            @Override
            public void changed(ObservableValue<? extends TuristickaAtrakcija> observable, TuristickaAtrakcija oldValue, TuristickaAtrakcija newValue) {

                RootController.selectedTA = newValue;
                //System.out.println(RootController.selectedTA);

            }
        });
    }

}
