package tn.pidev.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FacialRecognitionLogin extends Stage {
    public FacialRecognitionLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("facial_recognition_login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            setScene(scene);
            setTitle("Facial Recognition Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
