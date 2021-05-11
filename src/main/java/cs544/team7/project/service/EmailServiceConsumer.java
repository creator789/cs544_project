package cs544.team7.project.service;

import cs544.team7.project.model.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Component
@Log4j2
public class EmailServiceConsumer {

    @Value("${emailProducer}")
    String emailProducer;
    @Value("${passwordEmailProducer}")
    String passwordEmailProducer;

    @JmsListener(destination =  "${jsa.activemq.queue}", containerFactory = "jsaFactory")
    public void receiveMessage(Email email) throws MessagingException {
        log.info("Consumer received, sending email to <" + email + ">");
        sendEmail(email);
    }

    private void sendEmail(Email email) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");//25

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProducer, passwordEmailProducer);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(email.getTo(), false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
        msg.setSubject(email.getSubject());
        //msg.setContent(email.getBody(), "text/html"); other way to set the content without multipart
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(email.getBody(), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        /**
         #send image
         MimeBodyPart attachPart = new MimeBodyPart();
         attachPart.attachFile("/var/tmp/image19.png");
         multipart.addBodyPart(attachPart);
         */

        msg.setContent(multipart);
        Transport.send(msg);
    }
}