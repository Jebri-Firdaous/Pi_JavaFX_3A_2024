package tn.pidev.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/Acceuil.fxml"));
        //  HotelMail.send("ali.ammari@esprit.tn","liltek zine");
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("E-City");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/TourismeResources/icone_fenetre.png")));
        primaryStage.show();
    }

}
