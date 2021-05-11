package cs544.team7.project.controller;

import cs544.team7.project.model.Appointment;
import cs544.team7.project.model.Person;
import cs544.team7.project.model.PostForAppointment;
import cs544.team7.project.model.Session;
import cs544.team7.project.repository.PersonRepository;
import cs544.team7.project.service.AppointmentService;
import cs544.team7.project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("appointments")
    public List<Appointment> getAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("appointments")
    public Appointment createAppointment(@RequestBody PostForAppointment data) throws MessagingException, IllegalAccessException {
        Person person = personRepository.getById(data.getClient_id());
        Session session = sessionService.getSessionById(data.getSession_id());
        return appointmentService.makeReservation(person, session);
    }

}
