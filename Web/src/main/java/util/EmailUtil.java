package util;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailUtil {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "lkiet507@gmail.com";
    private static final String PASSWORD = "lcctwlcimuzjqcbt";

    public static void sendVerificationEmail(String recipientEmail, String verificationCode) {
        // Set up email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Email Verification");
            message.setText("Your verification code is: " + verificationCode);

            // Send email
            Transport.send(message);
            System.out.println("Verification email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
