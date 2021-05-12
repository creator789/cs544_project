package cs544.team7.project;

import cs544.team7.project.model.*;
import cs544.team7.project.repository.AppointmentRepository;
import cs544.team7.project.repository.PersonRepository;
import cs544.team7.project.repository.SessionRepository;
import cs544.team7.project.service.AppointmentService;
import cs544.team7.project.service.IAppointmentService;
import cs544.team7.project.service.ISessionService;
import cs544.team7.project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public void run(String...args) throws Exception {
        Role providerRole = new Role(RoleType.PROVIDER);
        Role clientRole = new Role(RoleType.CLIENT);
        Role adminRole = new Role(RoleType.ADMIN);
        Person client = new Person(
                "John", "Smith", "jmsvsmorone@gmail.com", "jsmith", encoder.encode("jsmith"),
                Arrays.asList(clientRole));
        Person admin = new Person(
                "Mike", "Doe", "jmsvsmorone@gmail.com", "admin", encoder.encode("admin"),
                Arrays.asList(adminRole, providerRole, clientRole));

        Session session = new Session(
                LocalDate.of(2021, Month.MAY, 16),
                LocalTime.now(),
                120,
                "Dalby Hall",
                admin
        );

        Appointment appointment = new Appointment(client, session);
        personRepository.save(client);
        personRepository.save(admin);
        sessionRepository.save(session);
        appointmentService.makeReservation(client,session);
    }
}
