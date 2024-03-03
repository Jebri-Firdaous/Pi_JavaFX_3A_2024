package tn.esprit.controllers.gestionMedecin;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import tn.esprit.entities.gestionMedecin.Medecin;
import tn.esprit.services.gestionMedecin.ServiceMedecin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.stage.Stage;

public class AfficherMedecinsController {

    @FXML
    public Label selectedDoctor;
    public TextField textFieldSearch;
    public Label msgErreurLabel;
    @FXML
    private ListView<Medecin> listViewMedecins;
    private final ServiceMedecin serviceMedecin = new ServiceMedecin();
    Medecin currentDoctorSelected;
    ObservableList<Medecin> medecinList;


    //    To can populate our list view with data
    /*public void initialize() {
        ObservableList<Medecin> medecinList = FXCollections.observableArrayList();
        listViewMedecins.setItems(medecinList);
        try {
            List<Medecin> medecinsFromService = serviceMedecin.afficher();
            medecinList.addAll(medecinsFromService);
            listViewMedecins.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentDoctorSelected = listViewMedecins.getSelectionModel().getSelectedItem();
//                    display selected item
                    *//*if (currentDoctorSelected != null) {
                        selectedDoctor.setText(currentDoctorSelected.toString());
                    } else {
                        selectedDoctor.setText("");
                    }*//*
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/
    public void initialize() {
        medecinList = FXCollections.observableArrayList();
        listViewMedecins.setItems(medecinList);
        try {
            List<Medecin> medecinsFromService = serviceMedecin.afficher();
            medecinList.addAll(medecinsFromService);

            // Set a custom CellFactory for the ListView
            listViewMedecins.setCellFactory(new Callback<ListView<Medecin>, ListCell<Medecin>>() {
                @Override
                public ListCell<Medecin> call(ListView<Medecin> param) {
                    return new ListCell<Medecin>() {
                        @Override
                        protected void updateItem(Medecin medecin, boolean empty) {
                            super.updateItem(medecin, empty);
                            if (empty || medecin == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                HBox hbox = new HBox();
                                hbox.setSpacing(85); // Adjust spacing as needed

                                // Add details to the HBox
                                Label nomLabel = new Label(medecin.getNom_medecin());
                                nomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;  -fx-pref-width: 130; -fx-pref-height: 21");

                                Label prenomLabel = new Label(medecin.getPrenom_medecin_medecin());

                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width: 118; -fx-pref-height: 21");
                                Label specialiteLabel = new Label(medecin.getSpecialite_medecin());
                                specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;  -fx-pref-width: 150; -fx-pref-height: 21");

                                Label numTelLabel = new Label(String.valueOf(medecin.getNumero_telephone_medecin()));
                                numTelLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width: 118; -fx-pref-height: 21");

                                Label adresseLabel = new Label(medecin.getAddress_medecin());
                                adresseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;  -fx-pref-width: 150; -fx-pref-height: 21");


                                // Add the TextFields to the HBox
                                hbox.getChildren().addAll(nomLabel, prenomLabel, specialiteLabel, numTelLabel, adresseLabel);

                                // Set the HBox as the graphic for the cell
                                setGraphic(hbox);

                            }
                        }
                    };
                }
            });

            listViewMedecins.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentDoctorSelected = listViewMedecins.getSelectionModel().getSelectedItem();
                    // Display the selected item
                   /* if (currentDoctorSelected != null) {
                        selectedDoctor.setText(currentDoctorSelected.getNom_medecin() + " - " + currentDoctorSelected.getSpecialite_medecin());
                    } else {
                        selectedDoctor.setText("");
                    }*/
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void BT_supprimer(ActionEvent actionEvent) {
        try {
            serviceMedecin.supprimer(currentDoctorSelected.getId_medecin());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToUpdatePage() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/ModifierMedecin.fxml"));
            Parent newPageRoot = loader.load();
            // Get the controller instance created by the FXMLLoader
            ModifierMedecinController modifierMedecinController = loader.getController();
            modifierMedecinController.initializeValues(currentDoctorSelected.getId_medecin(),
                    currentDoctorSelected.getNom_medecin(),
                    currentDoctorSelected.getPrenom_medecin_medecin(), currentDoctorSelected.getNumero_telephone_medecin(),
                    currentDoctorSelected.getAddress_medecin(), currentDoctorSelected.getSpecialite_medecin()
            );

            // Create a new scene with the newPageRoot
            Scene newPageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) selectedDoctor.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void switchToAddPage() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/AjouterMedecin.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/gestionMedecin/stylesForGM.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) selectedDoctor.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void BT_Modifier(ActionEvent actionEvent) {
        if(currentDoctorSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose one doctor ");
            alert.showAndWait();
            return;
        }
        switchToUpdatePage();
    }


    public void SwitchToAdd(ActionEvent actionEvent) {
        switchToAddPage();
    }

    public void searchByAttribut(KeyEvent keyEvent) throws SQLException {
        msgErreurLabel.setText("");
        msgErreurLabel.setTextFill(Color.RED);
        String valChamp = textFieldSearch.getText();
        String val = extractValue(valChamp);
        String attribut = extractAttribute(valChamp);
        if (textFieldSearch.getText().equals("")){
            initialize();
        }
        if (!attribut.toLowerCase().equals("nom") && !attribut.toLowerCase().equals("prenom") && !attribut.toLowerCase().equals("specialite")
        && !attribut.toLowerCase().equals("n°tel") && !attribut.toLowerCase().equals("adresse")){
            msgErreurLabel.setText("_attribut val");
            return;
        }
        msgErreurLabel.setText("");

//        if(valChamp!=null){
//           TextField erreur = new TextField();
//           erreur.setText("tu dois repecter le régle");
//           return;
//        }
        if (attribut.toLowerCase().equals("nom")){
            medecinList.clear();
            medecinList.addAll(serviceMedecin.searchByNom(val));
        }
        if (attribut.toLowerCase().equals("prenom")){
            medecinList.clear();
            medecinList.addAll(serviceMedecin.searchByPrenom(val));
        }
        if (attribut.toLowerCase().equals("prenom")){
            medecinList.clear();
            medecinList.addAll(serviceMedecin.searchByPrenom(val));
        }
        if (attribut.toLowerCase().equals("specialite")){
            medecinList.clear();
            medecinList.addAll(serviceMedecin.searchBySpacialite(val));
        }
        if (attribut.toLowerCase().equals("n°tel")){
            medecinList.clear();
            medecinList.addAll(serviceMedecin.searchByNum(val));
        }
        if (attribut.toLowerCase().equals("adresse")){
            medecinList.clear();
            medecinList.addAll(serviceMedecin.searchByAdresse(val));
        }


    }
    public static String extractAttribute(String input) {
        Pattern pattern = Pattern.compile("_(.+?)\\s");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // Retourne l'attribut
        } else {
            return "No attribute found.";
        }
    }

    public static String extractValue(String input) {
        Pattern pattern = Pattern.compile("\\s(.+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // Retourne la valeur
        } else {
            return "No value found.";
        }
    }
}
