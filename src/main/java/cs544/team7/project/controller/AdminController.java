package cs544.team7.project.controller;

import cs544.team7.project.model.Session;
import cs544.team7.project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private SessionService sessionService;

    @PostMapping("sessions")
    public Session createSession(@RequestBody @Valid Session session) {
        return sessionService.createSession(session);
    }

    @GetMapping("sessions")
    public List<Session> getSessions() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return sessionService.getAllSessions();
    }

    @GetMapping("sessions/available")
    public List<Session> getAvailableSessions() {
        return sessionService.getAllAvailableSessions();
    }

    @GetMapping("sessions/{id}")
    public Session getSessionById(@PathVariable(name = "id") int id) {
        return sessionService.getSessionById(id);
    }

    @PutMapping("sessions/{id}")
    public Session updateSession(@PathVariable(name = "id") int id,
                                  @RequestBody @Valid Session session) {
        session.setId(id);
        return sessionService.updateSession(session);
    }

    @PatchMapping("sessions/{id}")
    public Session updateSession2(@PathVariable(name = "id") int id,
                                 @RequestBody Session session) {
        session.setId(id);
        return sessionService.updateSession(session);
    }

    @DeleteMapping("sessions/{id}")
    public Session updateSession(@PathVariable(name = "id") int id) {
        return sessionService.deleteSession(
                sessionService.getSessionById(id)
        );
    }
}
