package tn.esprit.controllers.TourismeController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsController {
    public static final String ACCOUNT_SID = "AC815dcac2dccc12243bce95daa2ee6263";
    public static final String AUTH_TOKEN = "7d92093ba083aafaac0f063940ff7a5d";

    /*public static void sendSms(String recipientPhoneNumber, String messageBody){

         // Initialisation de Twilio
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

         // Numéro de téléphone Twilio
         String twilioPhoneNumber = "+15178887499";

         // Numéro de téléphone du destinataire
         recipientPhoneNumber = "+21692701943";

         messageBody = "Bonjour,Merci pour l'ajout du billet : Votre date de départ à ";

         // Envoi du SMS
         Message message = Message.creator(
                 new PhoneNumber(recipientPhoneNumber),
                 new PhoneNumber(twilioPhoneNumber),
                 messageBody
         ).create();

         // Affichage de l'ID du message en cas de succès
         System.out.println("Message sent successfully with SID: " + message.getSid());

  }}*/
    public static void sendSms(String recipientPhoneNumber, String messageBody) {
        // Initialisation de Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Numéro de téléphone Twilio
        String twilioPhoneNumber = "+19286155269";

        // Numéro de téléphone du destinataire
        /// String recipientPhoneNumber = "+21692701943";

        // Envoi du SMS
        Message message = Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();

        // Affichage de l'ID du message en cas de succès
        System.out.println("Message sent successfully with SID: " + message.getSid());
    }
}
