package cs544.team7.project.service;

import java.util.List;

import cs544.team7.project.model.Person;
import cs544.team7.project.model.Session;

public interface ISessionService {
	public Session createSession(Session s);
	public List<Session> getAllSessions();
	public List<Session> getAllSessionsByProvider(Person provider);
	public List<Session> getAllAvailableSessions();
	public List<Session> getAllAvailableSessionsByProvider(Person provider);
	public Session getSessionById(int id);
	public Session updateSession(Session s);
	public Session deleteSession(Session s);
}
