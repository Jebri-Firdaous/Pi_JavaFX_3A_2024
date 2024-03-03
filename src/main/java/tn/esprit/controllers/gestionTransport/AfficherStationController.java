package tn.esprit.controllers.gestionTransport;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.awt.Color;

import javafx.scene.input.MouseEvent;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entities.gestionTransport.Station;
import tn.esprit.services.gestionTransport.StationService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Date;

public class AfficherStationController {
    Station currentStationSelected;
    private final StationService ss = new StationService();
    @FXML
    private ListView<Station> listview;
    @FXML
    private Label stationLabel;
    @FXML
    private MenuButton crudTransport;
    @FXML
    private TextField searchfield;
    @FXML
    private TextArea pdf;
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    void initialize() {

        ObservableList<Station> observableList = FXCollections.observableArrayList();
        listview.setItems(observableList);

        try {
            List<Station> stations = ss.recuperer();
            observableList.addAll(stations);
            listview.setCellFactory(new Callback<ListView<Station>, ListCell<Station>>() {
                @Override
                public ListCell<Station> call(ListView<Station> param) {
                    return new ListCell<Station>() {
                        @Override
                        protected void updateItem(Station st, boolean empty) {
                            Station station;
                            super.updateItem(st, empty);
                            if (empty || st == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                // Assuming this code is inside a method where you have access to the rendezVous object
                                StationService sm = new StationService();
                                try {
                                    station = sm.getStationById(st.getId_station());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

                                String nom = st.getNom_station();
                                String adresse = st.getAdress_station();
                                String type = st.getType();


// Create an HBox to hold the details
                                HBox hbox = new HBox();
                                hbox.setSpacing(160); // Adjust spacing as needed


// Add details to the HBox with styled Labels
                                Label nomPrenomDoctorLabel = new Label(nom);
                                nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width: 180; -fx-pref-height:   21");
                                Label prenomLabel = new Label(adresse);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width: 180; -fx-pref-height:   21");
                                Label dureelabel = new Label(type);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");
                                hbox.getChildren().addAll(nomPrenomDoctorLabel, prenomLabel, dureelabel);
                                setGraphic(hbox);

                            }
                        }
                    };
                }
            });
            listview.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    currentStationSelected = listview.getSelectionModel().getSelectedItem();
                }
            });
            MenuItem ajouterItem = new MenuItem("Ajouter");

            ajouterItem.setOnAction(event -> ajouterAction());

//            crudTransport.getItems().addAll( ajouterItem);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
// Modifier la logique de filtrage pour prendre en compte la recherche avancée
     /*   searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Afficher tous les éléments si le champ de recherche est vide
                listview.setItems(observableList);
            } else {
                // Filtrer les éléments en fonction de la recherche avancée
                ObservableList<Station> filteredList = FXCollections.observableArrayList();
                String[] searchTerms = newValue.toLowerCase().split(","); // Séparer les termes de recherche par une virgule

                for (Station station : observableList) {
                    boolean matchesAllCriteria = true;

                    // Vérifier chaque terme de recherche
                    for (String term : searchTerms) {
                        // Vérifier si le terme correspond au nom, à l'adresse ou au type de la station
                        if (!(station.getNom_station().toLowerCase().contains(term.trim()) ||
                                station.getAdress_station().toLowerCase().contains(term.trim()) ||
                                station.getType().toLowerCase().contains(term.trim()))) {
                            matchesAllCriteria = false;
                            break; // Si un critère ne correspond pas, arrêter de vérifier les autres critères
                        }
                    }

                    if (matchesAllCriteria) {
                        filteredList.add(station);
                    }
                }*/
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Afficher tous les éléments si le champ de recherche est vide
                listview.setItems(observableList);
            } else {
                // Filtrer les éléments en fonction de la recherche avancée
                ObservableList<Station> filteredList = FXCollections.observableArrayList();
                String[] searchTerms = newValue.toLowerCase().split("\\s+"); // Séparer les termes de recherche par des espaces

                for (Station station : observableList) {
                    boolean matchesAllCriteria = true;

                    // Vérifier chaque terme de recherche
                    for (String term : searchTerms) {
                        // Vérifier si le terme correspond au nom, à l'adresse ou au type de la station
                        if (!(station.getNom_station().toLowerCase().contains(term) ||
                                station.getAdress_station().toLowerCase().contains(term) ||
                                station.getType().toLowerCase().contains(term))) {
                            matchesAllCriteria = false;
                            break; // Si un critère ne correspond pas, arrêter de vérifier les autres critères
                        }
                    }

                    if (matchesAllCriteria) {
                        filteredList.add(station);
                    }
                }
                listview.setItems(filteredList);
            }
        });


/*        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Afficher tous les éléments si le champ de recherche est vide
                listview.setItems(observableList);
            } else {
                // Filtrer les éléments en fonction de la recherche en temps réel par nom de station
                ObservableList<Station> filteredList = FXCollections.observableArrayList();
                for (Station station : observableList) {
                    boolean matchesAllCriteria = true;
                    if (station.getAdress_station().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(station);
                    }
                }
                listview.setItems(filteredList);
            }
        });*/

    }

    private void ajouterAction() {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/ajoutstation.fxml")));
            stationLabel.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }


    @FXML
    public void deletestation(javafx.event.ActionEvent actionEvent) {
     /*   try {
            ss.supprimer(currentStationSelected.getId_station());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Êtes-vous sûr de supprimer cette station ?");

        // Option pour confirmer ou annuler la suppression
        ButtonType confirmButton = new ButtonType("Confirmer");
        ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationDialog.getButtonTypes().setAll(confirmButton, cancelButton);

        // Récupération de la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        // Si l'utilisateur confirme la suppression
        if (result.isPresent() && result.get() == confirmButton) {
            try {
                // Suppression de la station
                ss.supprimer(currentStationSelected.getId_station());
                initialize();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void modify(javafx.event.ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/ModifierStation.fxml"));
        Parent newPageRoot = null;
        try {
            newPageRoot = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        // Get the controller instance created by the FXMLLoader
        ModifieStationController modifierStationController = loader.getController();
        modifierStationController.initializeValues(currentStationSelected.getId_station(),
                currentStationSelected.getNom_station(),
                currentStationSelected.getAdress_station(),
                currentStationSelected.getType()

        );

        // Create a new scene with the newPageRoot
        Scene newPageScene = new Scene(newPageRoot);

        // Get the current stage and set the new scene
        Stage stage = (Stage) stationLabel.getScene().getWindow();
        stage.setScene(newPageScene);

    }


    public void gobacktohome(ActionEvent actionEvent) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/home.fxml")));
            stationLabel.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void genererPdf(MouseEvent actionEvent) {
        try {
            // Création d'un nouveau document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Initialisation du contenu stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            BufferedImage image = ImageIO.read(new File("src/main/resources/resourcesGestionTransport/logo.png"));
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);
            float scale = 0.2f; // Echelle de l'image (50% dans cet exemple)
            contentStream.drawImage(pdImage, 10, 650, pdImage.getWidth() * scale, pdImage.getHeight() * scale);

            // Définition des paramètres de style
            PDType1Font fontBold = PDType1Font.TIMES_ROMAN;
            PDType1Font fontRegular = PDType1Font.HELVETICA;
            int fontSizeTitle = 40;
            int fontSizeHeader = 20;
            int fontSizeNormal = 15;
            float leading = 4f * fontSizeNormal;
            float leadingg = 3f * fontSizeHeader;

            // Tableau des en-têtes
            String[] headers = {"Nom", "Adresse", "Type"};

            // Récupération de la liste des stations
            ObservableList<Station> items = listview.getItems();

            // Création du tableau
            float margin = 150;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float tableMargin = 20;

            // Définir la couleur du titre "Liste des stations"
            Color titleColor = new Color(65, 105, 225);

            contentStream.beginText();
            contentStream.setFont(fontBold, fontSizeTitle);
            contentStream.setNonStrokingColor(titleColor); // Définir la couleur du titre
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Liste des stations");
            contentStream.newLineAtOffset(0, -leadingg);
            contentStream.endText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            contentStream.beginText();
            contentStream.setFont(fontRegular, fontSizeNormal);
            contentStream.setNonStrokingColor(Color.BLACK); // Changer la couleur en noir
            contentStream.newLineAtOffset(margin + 20, yPosition - leadingg - fontSizeNormal + 200); // Déplacer la position de la date
            contentStream.showText("Date du rapport : " + currentDate);
            contentStream.endText();
            // Réinitialiser la couleur du texte pour le reste du contenu
            contentStream.setNonStrokingColor(Color.BLACK);

            // Dessiner les en-têtes du tableau
            float cellWidth = tableWidth / headers.length;
            float xPosition = margin - 30; // Position de départ initiale

            for (String header : headers) {
                contentStream.beginText();
                contentStream.setFont(fontBold, fontSizeHeader);
                float textWidth = fontBold.getStringWidth(header) / 1000f * fontSizeHeader;
                float centeredPosition = xPosition + (cellWidth - textWidth) / 2; // Position centrée dans la cellule
                contentStream.newLineAtOffset(centeredPosition, yPosition - leading);
                contentStream.showText(header);
                contentStream.endText();
                xPosition += cellWidth; // Mettre à jour la position de départ pour la prochaine cellule
            }

            // Dessiner les lignes du tableau
            yPosition -= rowHeight + tableMargin;
            for (Station station : items) {
                xPosition = margin; // Réinitialiser la position x pour chaque ligne
                contentStream.beginText();
                contentStream.setFont(fontRegular, fontSizeNormal);
                contentStream.newLineAtOffset(xPosition, yPosition - leading);
                contentStream.showText(station.getNom_station());

                xPosition += cellWidth; // Déplacer la position x à la prochaine cellule
                contentStream.newLineAtOffset(cellWidth, 0);
                contentStream.showText(station.getAdress_station());

                xPosition += cellWidth; // Déplacer la position x à la prochaine cellule
                contentStream.newLineAtOffset(cellWidth, 0);
                contentStream.showText(station.getType());
                contentStream.endText();

                yPosition -= rowHeight + tableMargin; // Déplacer la position y à la prochaine ligne
            }

            // Fermeture du content stream
            contentStream.close();

            // Sauvegarde du document PDF
            document.save("Stations.pdf");
            document.close();

            // Ouvrir automatiquement le fichier PDF après sa création
            File pdfFile = new File("Stations.pdf");
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs éventuelles
        }
    }


    public void addstation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/ajoutstation.fxml"));
            Parent newPageRoot = loader.load();
            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) stationLabel.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



