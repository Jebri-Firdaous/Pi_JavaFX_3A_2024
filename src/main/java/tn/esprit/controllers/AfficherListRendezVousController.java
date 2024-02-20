package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import tn.esprit.entities.Medecin;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.ServiceMedecin;
import tn.esprit.services.ServiceRendezVous;

import java.sql.SQLException;
import java.util.List;

public class AfficherListRendezVousController {
    public ListView<RendezVous> listViewRendezVous;
    RendezVous currentRendezVousSelected;
    private final ServiceRendezVous serviceRendezVous = new ServiceRendezVous();

    public void initialize() {
        ObservableList<RendezVous> listRndezVous = FXCollections.observableArrayList();
        listViewRendezVous.setItems(listRndezVous);
        try {
            List<RendezVous> rendezvousFromService = serviceRendezVous.afficher();
            listRndezVous.addAll(rendezvousFromService);

            // Set a custom CellFactory for the ListView
            listViewRendezVous.setCellFactory(new Callback<ListView<RendezVous>, ListCell<RendezVous>>() {
                @Override
                public ListCell<RendezVous> call(ListView<RendezVous> param) {
                    return new ListCell<RendezVous>() {
                        @Override
                        protected void updateItem(RendezVous rendezVous, boolean empty) {
                            super.updateItem(rendezVous, empty);
                            if (empty || rendezVous == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                // Customize the display of the RendezVous object
                                ServiceMedecin sm = new ServiceMedecin();
                                Medecin medecin = sm.getMedecinById(rendezVous.getId_medecin());
                                String doctorName = medecin.getNom_medecin();
                                String doctorSurname = medecin.getPrenom_medecin_medecin();
                                String specialty = medecin.getSpecialite_medecin();
                                int phoneNumber = medecin.getNumero_telephone_medecin();
                                String address = medecin.getAddress_medecin();
                                String formattedText = String.format("Dr. %s %s - %s Phone: %s Address: %s",
                                        doctorName, doctorSurname, specialty, phoneNumber, address);
                                setText(formattedText);
                            }
                        }
                    };
                }
            });

            listViewRendezVous.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RendezVous>() {
                @Override
                public void changed(ObservableValue<? extends RendezVous> observableValue, RendezVous oldRendezVous, RendezVous newRendezVous) {
                    currentRendezVousSelected = newRendezVous;
                    // Display the selected item
                    if (currentRendezVousSelected != null) {
                        // Assuming you have a Label to display the selected RendezVous
                        // selectedRendezVousLabel.setText(currentRendezVousSelected.toString());
                    } else {
                        // Assuming you have a Label to display the selected RendezVous
                        // selectedRendezVousLabel.setText("");
                    }
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void suprrimerRDBT(ActionEvent actionEvent) {
        try {
            serviceRendezVous.supprimer(currentRendezVousSelected.getRef_rendez_vous());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
