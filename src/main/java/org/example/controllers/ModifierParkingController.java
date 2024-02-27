package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.example.entities.Parking;
import org.example.services.ParkingService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ModifierParkingController {
    private final ParkingService ps = new ParkingService();
    public TextField nomTF;
    public TextField addresseTF;
    public TextField nbPlacesTF;
    public TextField idTF;
    public Label errNom;
    public Label errAddr;
    public Label errNb;
    int id;
    private boolean test1=true, test2=true, test3=true;

    public void init(String nom, String addresse, int nbPlaces, int id) {
        this.nomTF.setText(nom);
        this.addresseTF.setText(addresse);
        this.nbPlacesTF.setText(Integer.toString(nbPlaces));
        this.id=id;
//        Stage stage=(Stage) nomTF.getScene().getWindow();
//        Parking parking=(Parking) stage.getUserData();
//        System.out.println(2);
        //nomTF.setText(parking.getNom());
//            try {}
//        catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
    }

    public void naviguerVersAffichage(ActionEvent actionEvent) {
        try{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherParking.fxml")));
        nomTF.getScene().setRoot(root);
    } catch (
    IOException e) {
        System.err.println(e.getMessage());
    }
    }

    public void Update(ActionEvent actionEvent){
        if(nomTF.getText().isBlank()) {
            errNom.setText("Ce champ est obligatoire!");
            //tst=false;
        }
        if(addresseTF.getText().isBlank()) {
            errAddr.setText("Ce champ est obligatoire!");
            //tst=false;
        }
        if(nbPlacesTF.getText().isBlank()) {
            errNb.setText("Ce champ est obligatoire!");
            //tst=false;
        }
        if(test1 && test2 && test3) {
            try {
                //System.out.println(ps.recupererUn(Integer.parseInt(idTF.getText())).getRef());
                Parking p = new Parking(id,
                        nomTF.getText(),
                        addresseTF.getText(),
                        Integer.parseInt(nbPlacesTF.getText()));
//            p.setRef(2);
//            p.setNom("hhh");
//            p.setAddresse("yyy");
//            p.setNbPlaceMax(20);
                ps.modifier(p);
//            ps.modifier(ps.recupererUn(Integer.parseInt(idTF.getText())));
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherParking.fxml")));
                nomTF.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
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

        nbPlacesTF.setTextFormatter(formatter);
        if(!nbPlacesTF.getText().isEmpty()) {
            if (nbPlacesTF.getText().matches("[0-9]+")) {
                errNb.setText("");
                if (Integer.parseInt(nbPlacesTF.getText())<=100) {
                    errNb.setText("");
                    try {
                        if (Integer.parseInt(nbPlacesTF.getText()) >= ps.calculNbPlace(id)){
                            errNb.setText("");
                            test3=true;
                        }else{
                            errNb.setText("Nombre inferieur au nbPlace existant!");
                            test3=false;
                        }
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
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