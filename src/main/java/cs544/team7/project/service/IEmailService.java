package cs544.team7.project.service;

import cs544.team7.project.model.Email;
import cs544.team7.project.model.Person;

import javax.mail.MessagingException;

public interface IEmailService {
	public void sendMessage(Person person, String body) throws MessagingException;
	public void sendMessage(String to, String body) throws MessagingException;
}
