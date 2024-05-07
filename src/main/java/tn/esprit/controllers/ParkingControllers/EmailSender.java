package tn.esprit.controllers.ParkingControllers;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String recipient, String subject, String body, Image I) throws MessagingException {

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
            // Convert the Image object to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(I, null);
            try {
                ImageIO.write(bufferedImage, "png", baos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] imageBytes = baos.toByteArray();

            // Create the HTML email body with the image
            String htmlBody = "<html><body><p>" + body + "</p><img src=\"cid:image1\"></body></html>";

            // Create the image part
            MimeBodyPart imagePart = new MimeBodyPart();
            DataSource fds = new ByteArrayDataSource(imageBytes, "image/png");
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<image1>");

            // Create the multipart to hold the HTML body and the image
            Multipart multipart = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(imagePart);

            // Set the multipart as the email content
            message.setContent(multipart);

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");

        } catch (MessagingException e) {
            throw new MessagingException("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        /*try {
            sendEmail("recipient@example.com", "Test Subject", "This is a test email message.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }
}