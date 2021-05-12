package cs544.team7.project.service;

import java.util.List;

import cs544.team7.project.model.Appointment;
import cs544.team7.project.model.AppointmentStatus;
import cs544.team7.project.model.Person;
import cs544.team7.project.model.Session;

import javax.mail.MessagingException;

public interface IAppointmentService {
	public Appointment makeReservation(Person p, Session s) throws IllegalAccessException, MessagingException;
	public boolean cancelAppointment(Person p, Appointment a) throws MessagingException;
	public List<Appointment> getAllPendingAppointmentsForSession(Person p, Session s);
	public Appointment getAppintmentById(int id);
	public List<Appointment> getApprovedAppointments();
	public List<Appointment> getCanceledAppointments();
	public List<Appointment> getAllAppointments();
	public boolean approveAppointment(Person p, Appointment a) throws MessagingException;
	public void deleteAppointment(Person p, Appointment a);
	public void updateAppointment(Person p, Appointment a);

	//Client
	//List<Appointment> getAllAppointmentsByClient(Person client);
	//List<Appointment> getApprovedAppointmentsByClient(Person client);
	//List<Appointment> getAppointmentsByStatus(AppointmentStatus status);


}
