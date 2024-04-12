package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {
    public static void sendOTP(String email, String OTP) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "thirwanivishal3@gmail.com";

        String password = "qgfwvtarvvuogqdg";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // SMTP server configuration
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("OTP for File Encryption");
            // Now set the actual message
            message.setText("Your One Time Password for File Encyrption Application is " + OTP);

            System.out.println("Sending the Mail....");
            // Send message
            Transport.send(message);
            System.out.println("Mail sent successfully....");
        } catch (MessagingException mEx) {
            mEx.printStackTrace();
        }
    }
}
