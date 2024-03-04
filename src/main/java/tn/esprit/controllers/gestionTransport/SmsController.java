package tn.esprit.controllers.gestionTransport;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsController {
    public static final String ACCOUNT_SID = "AC9b81b4f943aab8c49b38b51f886332a2";
    public static final String AUTH_TOKEN = "fa78ca13f2be95eca59e80e2e933766f";

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
   public static void sendSms(String recipientPhoneNumber,String messageBody) {
       // Initialisation de Twilio
       Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

       // Numéro de téléphone Twilio
       String twilioPhoneNumber = "+15178887499";

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
   }}

