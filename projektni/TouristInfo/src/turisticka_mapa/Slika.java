/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turisticka_mapa;

import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Dell
 */
public class Slika extends Thread {

    private File putanja;
    private Stage stage;

    public Slika(File putanjaSlika, Stage stage) {
        this.putanja = putanjaSlika;
        this.stage = stage;
    }

    public void run() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    Pane root = new Pane();
                    ImageView imageView = new ImageView(new Image(putanja.toURI().toURL().toString(), 300, 300, false, false));
                    root.getChildren().add(imageView);
                    Scene scene = new Scene(root, 300, 300);
                    stage.setScene(scene);
                    stage.show();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        try {
            this.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.close();
            }
        });

    }

    public File getPutanja() {
        return putanja;
    }

    public void setPutanja(File putanjaSlika) {
        this.putanja = putanja;
    }
}
