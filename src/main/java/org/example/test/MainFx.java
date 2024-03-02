package org.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Acceuil.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("E-City");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icone_fenetre.png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}