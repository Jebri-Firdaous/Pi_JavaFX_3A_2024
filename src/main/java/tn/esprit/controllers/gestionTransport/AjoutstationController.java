package tn.esprit.controllers.gestionTransport;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.entities.gestionTransport.Station;
import java.io.IOException;
import tn.esprit.services.gestionTransport.StationService;
import org.controlsfx.control.Notifications;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;

public class AjoutstationController {
    StationService ss = new StationService();
    @FXML
    private TextField tadress;
    @FXML
    private TextField tnom;
    @FXML
    private MenuButton crudTransport;
    @FXML
    private ComboBox<String>ttype1;
    @FXML
    public void addservice(ActionEvent actionEvent) {
      /*  try {
            ss.ajouter(new Station(tnom.getText(), tadress.getText(), ttype.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errorrr");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }*/
        /********CONTROLE DE SAISIE**********/
        try {
            // Récupération des valeurs des champs de saisie
            String nom = tnom.getText().trim();
            String adresse = tadress.getText().trim();
            Object selectedObject = ttype1.getValue();
            String type = (selectedObject != null) ? selectedObject.toString() : "";

            // Vérification des champs de saisie
            if (nom.isEmpty() || adresse.isEmpty() ) {
                // Affichage d'un message d'erreur si l'un des champs est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return; // Arrêter l'exécution de la méthode si l'un des champs est vide
            }

            // Vérification que l'adresse ne contient pas des entiers
            boolean containsDigit = false;
            for (char c : adresse.toCharArray()) {
                if (Character.isDigit(c)) {
                    containsDigit = true;
                    break;
                }
            }
            if (containsDigit) {
                // Affichage d'un message d'erreur si l'adresse contient des entiers
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("L'adresse ne doit pas contenir des entiers.");
                alert.showAndWait();
                return; // Arrêter l'exécution de la méthode si l'adresse contient des entiers
            }


            if (ss.stationExists(nom, adresse)) {
                // Affichage d'un message d'erreur si une station avec le même nom et la même adresse existe déjà
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une station avec le même nom et la même adresse existe déjà.");
                alert.showAndWait();
                return; // Arrêter l'exécution de la méthode si la station existe déjà
            }

            // Appel de la méthode d'ajout avec les valeurs des champs de saisie
            ss.ajouter(new Station(nom, adresse, ttype1.getValue()));
            Image image = new Image("/resourcesGestionTransport/img.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            Notifications.create()
                    .title("E-City")
                    .text("Station Ajouté")
                    .darkStyle()
                    .graphic(imageView)
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .show();
            tnom.clear();
            tadress.clear();
        }
        catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void afficherstation(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            tnom.getScene().setRoot(root);

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void suppstation(ActionEvent actionEvent) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            tnom.getScene().setRoot(root);

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void modifier(ActionEvent actionEvent) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            tnom.getScene().setRoot(root);

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void initialize () {
        initialize(null, null);
    }

    public void initialize (URL location, ResourceBundle resources) {
        // Create menu items and add them to the MenuButton
        // Créer des éléments de menu
        System.out.println("methode");
        MenuItem modifierItem = new MenuItem("Modifier");
        MenuItem afficherItem = new MenuItem("Afficher");
        MenuItem supprimerItem = new MenuItem("Supprimer");

        // Associer des actions aux éléments de menu

        modifierItem.setOnAction(event -> modifierAction());
        afficherItem.setOnAction(event -> afficherAction());
        supprimerItem.setOnAction(event -> supprimerAction());

        // Ajouter les éléments de menu au MenuButton
        //crudTransport.getItems().addAll( modifierItem, afficherItem, supprimerItem);
        System.out.println("item");
    }

    private void modifierAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            tnom.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void switchToDisplayAllstationsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml"));
            Parent newPageRoot = loader.load();


            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) tnom.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void afficherAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            tnom.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void supprimerAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            tnom.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void backtohome(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/home.fxml")));
            tnom.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    }

    





