package cs544.team7.project.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cs544.team7.project.model.Appointment;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	
}
