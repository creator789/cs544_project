package cs544.team7.project.service;

import cs544.team7.project.model.Email;
import cs544.team7.project.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Log4j2
public class EmailServiceProducer implements IEmailService{

    @Autowired
    JmsTemplate jmsTemplate;
    @Value("${jsa.activemq.queue}")
    String queue;

    @Override
    public void sendMessage(Person person, String body) throws MessagingException {
        log.info("Sending an email to "+person.getEmail());
        sendMessage(new Email(person.getEmail(),"css544 to person email",body));
    }

    @Override
    public void sendMessage(String to, String body) throws MessagingException {
        log.info("Sending an email to "+to);
        sendMessage(new Email(to,"default subject",body));
    }


    public void sendMessage(Email e) throws MessagingException {
        //sending messsage
        jmsTemplate.convertAndSend(queue, e);
    }
}