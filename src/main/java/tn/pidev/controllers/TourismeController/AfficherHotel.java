package tn.pidev.controllers.TourismeController;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.pidev.entities.TourismeEntities.Hotel;
import tn.pidev.services.TourismeService.ServiceHotel;
import tn.pidev.utiles.QrCodeHotel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherHotel {

    private final ServiceHotel sh = new ServiceHotel();
    @FXML
    public Label SelectedHotel;
    @FXML
    private TextField rechercheHotelTF;
    private Stage stage;
    @FXML
    private ListView<Hotel> listeView;
    @FXML
    private Text myText;
    @FXML
    private ImageView qrCode;
    // Hotel currentHotelSelected;






    /*--------------------------------------------------Recuperatin_des_données_dans_base------------------------------------------------------*/

    public void initialize() {


        try {

            //////////////////////////////////////////rechercher//////////////////////////////////////////////////////
            rechercherHotelsDansLaBase("");
            rechercheHotelTF.textProperty().addListener((observable, oldValue, newValue) -> {
                rechercherHotelsDansLaBase(newValue);
            });


            // Récupère la liste des hôtels depuis le service
            List<Hotel> hotels = sh.afficher();
            listeView.getSelectionModel().selectedItemProperty().addListener((observableValue, hotel, t1) -> {
                Hotel selectedHotel = listeView.getSelectionModel().getSelectedItem();


/////////////////////////////////////////////QRCODE///////////////////////////////////////////////////////////////
                BufferedImage bufferedImage = null;

                try {
                    bufferedImage = QrCodeHotel.generateQRCodeImage(selectedHotel.getNom_hotel());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                WritableImage image = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
                SwingFXUtils.toFXImage(bufferedImage, image);


                qrCode.setImage(image);


            });

            // Crée une cellule personnalisée pour la ListView
            listeView.setCellFactory(param -> new ListCell<Hotel>() {
                @Override
                protected void updateItem(Hotel hotel, boolean empty) {
                    super.updateItem(hotel, empty);

                    if (empty || hotel == null) {
                        // Si la cellule est vide ou l'objet hôtel est null, ne rien afficher
                        setText(null);
                    } else {
                        // Crée une VBox pour aligner verticalement les valeurs des attributs
                        VBox vbox = new VBox();
                        vbox.setSpacing(5);
                        vbox.setPadding(new Insets(5));

                        // Ajoute des Labels pour chaque attribut
                        Label nomLabel = new Label("Nom: " + hotel.getNom_hotel());
                        Label adresseLabel = new Label("Adresse: " + hotel.getAdress_hotel());
                        Label prix1Label = new Label("Prix 1: " + hotel.getPrix1() + " DT");
                        Label prix2Label = new Label("Prix 2: " + hotel.getPrix2() + " DT");
                        Label prix3Label = new Label("Prix 3: " + hotel.getPrix3() + " DT");
                        Label numero1Label = new Label("numero normal: " + hotel.getNumero1() + " DT");
                        Label numero2Label = new Label("numero standard: " + hotel.getNumero2() + " DT");
                        Label numero3Label = new Label("numero luxe: " + hotel.getNumero3() + " DT");
                        // Ajoute les Labels à la VBox
                        vbox.getChildren().addAll(nomLabel, adresseLabel, prix1Label, prix2Label, prix3Label, numero1Label, numero2Label, numero3Label);

                        // Affiche la VBox dans la cellule
                        setGraphic(vbox);
                    }
                }
            });

            // Ajoute les hôtels à la ListView
            listeView.getItems().addAll(hotels);


        } catch (SQLException e) {
            // Affiche une alerte en cas d'erreur
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la récupération des hôtels : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }



    /*--------------------------------------------------SELECTION_ET_NAVIGUER_VERS_MODIF------------------------------------------------------*/

    @FXML
    void bt_modifier(ActionEvent event) {


        // Récupérer l'objet Hotel sélectionné dans la ListView
        Hotel selectedHotel = listeView.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            // L'objet Hotel est sélectionné, vous pouvez maintenant l'utiliser comme vous le souhaitez
            switchToUpdatePage(selectedHotel);
        } else {
            // Aucun hôtel sélectionné, afficher un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un hôtel à modifier.");
            alert.showAndWait();
            //  ToModifierListeHotel() ;
        }
    }


    // Méthode pour passer l'objet Hotel à la page de modification


    /*-------------------------------------------------- passer-Hotel-à-modification------------------------------------------------------*/

    private void switchToUpdatePage(Hotel hotel) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/ModifierHotel.fxml"));
            Parent root = loader.load();

            ModifierHotel modifierArticleController = loader.getController();
            modifierArticleController.setHotel(hotel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }


    }

    /*--------------------------------------------------Afficher-Alerte------------------------------------------------------*/

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    /*--------------------------------------------------Supprimer------------------------------------------------------*/

    @FXML
    void boutonSupprimer(ActionEvent event) {
// Récupérer l'hôtel sélectionné dans la liste
        Hotel hotelASupprimer = listeView.getSelectionModel().getSelectedItem();

        if (hotelASupprimer != null) {
            // Supprimer l'hôtel de la base de données et de la liste affichée
            try {
                sh.supprimer(hotelASupprimer.getId_hotel());
                listeView.getItems().remove(hotelASupprimer);

                // Afficher un message de confirmation
                afficherMessage("Suppression réussie", "L'hôtel a été supprimé avec succès.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                // Afficher un message d'erreur en cas d'échec de la suppression
                afficherMessage("Erreur", "Erreur lors de la suppression de l'hôtel : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            // Si aucun hôtel n'est sélectionné, afficher un message d'avertissement
            afficherMessage("Aucune sélection", "Veuillez sélectionner un hôtel à supprimer.", Alert.AlertType.WARNING);
        }
    }

    /*--------------------------------------------------Afficher_Message------------------------------------------------------*/
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    /*--------------------------------------------------Rechercher------------------------------------------------------*/
    private void rechercherHotelsDansLaBase(String recherche) {
        listeView.getItems().clear(); // Effacer les anciens résultats de la ListView
        try {
            List<Hotel> hotelsTrouves = sh.rechercherHotelsDansLaBase(recherche);
            if (!hotelsTrouves.isEmpty()) {
                // Si des hôtels ont été trouvés, les ajouter à la ListView
                listeView.getItems().addAll(hotelsTrouves);
            } else {
                // Si aucun hôtel n'a été trouvé, afficher un message d'erreur
                afficherAlerte(Alert.AlertType.INFORMATION, "Résultat de la recherche", "Aucun hôtel correspondant trouvé.");
            }
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche des hôtels : " + e.getMessage());
        }
    }


}