package tn.pidev.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;
import tn.pidev.entities.Parking;
import tn.pidev.services.ParkingService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AjouterParkingController {
    private final ParkingService ps = new ParkingService();
    @FXML
    public TextField nomTF;
    @FXML
    public TextField addresseTF;
    public TextField nbMaxTF;
    public Label errNom;
    public Label errAddr;
    public Label errNb;
    public TextField coordTF;
    public Label errCoord;
    public Label errLongi;
    public TextField longiTF;
    private boolean test1, test2, test3, test4, test5=true;

    @FXML
    public void ajouterParking(javafx.event.ActionEvent actionEvent) {
        boolean tst=true;
        if(nomTF.getText().isBlank()) {
            errNom.setText("Ce champ est obligatoire!");
            //tst=false;
        }
        if(addresseTF.getText().isBlank()) {
            errAddr.setText("Ce champ est obligatoire!");
            //tst=false;
        }
        if(nbMaxTF.getText().isBlank()) {
            errNb.setText("Ce champ est obligatoire!");
            //tst=false;
        }
        if(coordTF.getText().isBlank()) {
            errCoord.setText("Ce champ est obligatoire!");
            test4=false;
        }
        else {
            errCoord.setText("");
            test4=true;
        }
        if(longiTF.getText().isBlank()) {
            errLongi.setText("Ce champ est obligatoire!");
            test5=false;
        }
        else {
            errLongi.setText("");
            test5=true;
        }
        if(test1 && test2 && test3 && test4 && test5) {
            try {
                System.out.println(Float.parseFloat(coordTF.getText()));
                ps.ajouter(new Parking(nomTF.getText(), addresseTF.getText(), Integer.parseInt(nbMaxTF.getText()), 0, Float.parseFloat(coordTF.getText()), Float.parseFloat(longiTF.getText()), "Disponible"));
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherParkingsss.fxml")));
                nomTF.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void naviguezVersAffichage(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherParkingsss.fxml")));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void testNom(KeyEvent keyEvent) {
        if(!nomTF.getText().isEmpty()) {
            if (Character.isUpperCase(nomTF.getText().charAt(0)) && nomTF.getText().matches("[a-zA-Z]+")) {
                errNom.setText("");
                if (nomTF.getText().length()<=10) {
                    errNom.setText("");
                    test1=true;
                } else {
                    errNom.setText("Longueur du Nom doit etre <10!");
                    test1=false;
                }
            } else {
                errNom.setText("Nom doit commencer par majuscule!");
                test1=false;
            }
        }else {
            errNom.setText("");
            test1 = false;
        }
    }

    public void testAddresse(KeyEvent keyEvent) {
        if(!addresseTF.getText().isEmpty()) {
            if (Character.isUpperCase(addresseTF.getText().charAt(0)) && addresseTF.getText().matches("[a-zA-Z]+")) {
                errAddr.setText("");
                if (addresseTF.getText().length()<=10) {
                    errAddr.setText("");
                    test2=true;
                } else {
                    errAddr.setText("Longueur du Addresse doit etre <10!");
                    test2=false;
                }
            } else {
                errAddr.setText("Addresse doit commencer par majuscule!");
                test2=false;
            }
        }else {
            errAddr.setText("");
            test2 = false;
        }
    }

    public void testNb(KeyEvent keyEvent) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });

        nbMaxTF.setTextFormatter(formatter);
        if(!nbMaxTF.getText().isEmpty()) {
            if (nbMaxTF.getText().matches("[0-9]+")) {
                errNb.setText("");
                if (Integer.parseInt(nbMaxTF.getText())<=100) {
                    errNb.setText("");
                    test3=true;
                } else {
                    errNb.setText("Nombre doit etre <=100!");
                    test3=false;
                }
            } else {
                errNb.setText("Caractere non numerique!");
                test3=false;
            }
        }else {
            errNb.setText("");
            test3 = false;
        }
    }
}