package tn.pidev.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author HP
 */
public class ChatController implements Initializable {

    private static final int MAX_ALERTS = 3;
    private static final int BAN_DURATION_MINS = 5;
    private static final String[] FORBIDDEN_WORDS = {"fuck", "shutup", "damn"};
    @FXML
    private TextArea textchat;
    @FXML
    private TextField ASK;
    private Map<String, String> responses;
    @FXML
    private Button Home;
    private int alertCount = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        responses = new HashMap<>();
        responses.put("bonjour", "comment puis je vous aidez");
        responses.put("quelle est votre banque", "BANEKTI");
        responses.put("systeme des packs", "lorsque vous acheter un pack vous allez obtenir plusieurs bonus ! essayer et vous ne regretter pas ");
        responses.put("des bals de fin d'année ?", "-15% ***** sur chaque Achat");
        responses.put("donne moi les administrateurs disponibles", "Nahed harrath , Darine Dhifallah , Ons Bel Hadj , Ezzedine Benzima , Firdews Jebri , Fers Felhi . ");
        responses.put("salut", "salut ");
        responses.put("who are you ?", "je suis un bot créer par Hammoudi Ayoub ");
        responses.put("Banekti?", "Banque Tunisienne fondée par Hammoudi Ayoub");
        responses.put("pack silver ?", "vous allez profitez de plusieurs bonus en achetant le pack silver :------------ ");
        responses.put("pack gold ?", "vous allez profitez de plusieurs bonus en achetant le pack gold : -----------");
        responses.put("j'ai une question", "Bien sûr! Je suis ici pour vous aider. Quelle est votre question ?");

    }

    @FXML
    private void UserA(ActionEvent event) throws Exception {
        String input = ASK.getText();
        String response = responses.getOrDefault(input, " contacter moi sur mail ecity.tunis2000@gmail.com pour plus d'informations");
        for (String forbiddenWord : FORBIDDEN_WORDS) {
            if (input.contains(forbiddenWord)) {
                alertCount++;
                if (alertCount == MAX_ALERTS) {
                    // Ban the user for 5 minutes
                    ASK.setDisable(true);
                    Alert alert = new Alert(AlertType.ERROR, "Vous avez été banni ");
                    alert.showAndWait();
                    //  Mail.sendMail("ayoub.hammoudi@esprit.tn", "https://buy.stripe.com/test_aEU166gaWcCj1S86op");
                    Platform.exit();
                } else {
                    Alert alert = new Alert(AlertType.WARNING, "Le message contient un gros mot. Attention!");
                    alert.showAndWait();
                }
                ASK.clear();
                return;
            }
        }
        textchat.appendText("User: " + input + "\n");
        textchat.appendText("Chatbot: " + response + "\n\n");
        ASK.clear();
    }

    @FXML
    private void OnHomeClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BackHome.fxml"));
            Parent root = loader.load();
            Home.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AjouterReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

