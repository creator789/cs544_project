package cs544.team7.project;

import cs544.team7.project.model.*;
import cs544.team7.project.repository.SessionRepository;
import cs544.team7.project.service.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class Application {

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory("vm://localhost");
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	/*
	 * Used for Receiving Message
	 */
	@Bean
	public JmsListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setMessageConverter(jacksonJmsMessageConverter());
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	/*
	 * Used for Sending Messages.
	 */
	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setMessageConverter(jacksonJmsMessageConverter());
		template.setConnectionFactory(connectionFactory());
		return template;
	}

	public static void main(String[] args) throws MessagingException, IllegalAccessException {
		ApplicationContext context = SpringApplication.run(Application.class, args);

	}



}
