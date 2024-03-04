package tn.esprit.controllers.gestionUser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String recipient, String subject, String body) throws MessagingException {

        // Paramètres SMTP
        String host = "smtp.gmail.com";
        String port = "587"; // Port SMTP
        String username = "ecity.tunis2000@gmail.com";
        String password = "nsyy dwge swzu oshg";

        // Propriétés de connexion
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Création de la session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");

        } catch (MessagingException e) {
            throw new MessagingException("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            sendEmail("recipient@example.com", "Test Subject", "This is a test email message.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
