package ai;

import com.sun.mail.smtp.SMTPTransport;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * @author liujd65
 * @date 2021/11/23 11:35
 **/
public class Main {
    public static void main(String[] args) {
        //provide recipient's email ID
        String to = "1531653614@qq.com";

        //provide sender's email ID
        String from = "liudd0109@163.com";
        //provide Mailtrap's username
        final String username = "liudd0109@163.com";
        //provide Mailtrap's password
        final String password = "LKJSMYLEDSFLJLUX";

        //provide Mailtrap's host address
        String host = "smtp.163.com";
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();

        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");

        //create the Session object
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            //create a MimeMessage object
            Message message = new MimeMessage(session);

            //set From email field
            message.setFrom(new InternetAddress(from));

            //set To email field
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            //set email subject field
            message.setSubject("Here comes Jakarta Mail!");

            //set the content of the email message
            message.setText("Just discovered that Jakarta Mail is fun and easy to use");

            //send the email message
            //Transport.send(message);
            SMTPTransport.send(message);
            System.out.println("Email Message Sent Successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
