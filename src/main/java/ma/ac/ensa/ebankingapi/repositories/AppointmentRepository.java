package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT apt.id " +
                "FROM appointments apt " +
                "WHERE apt.agent_id = ?1 AND " +
                "apt.date=?2 AND " +
                "(" +
                "(apt.start_time BETWEEN ?3 AND ?4) or " +
                "(apt.end_time BETWEEN ?3 AND ?4) or " +
                "(apt.start_time <= ?3 AND apt.end_time > ?3)" +
                ") " +
                "LIMIT 1", nativeQuery = true)
    Long existsByAgentIdAndDateAndStartTimeAndEndTime(Long agentId, LocalDate date, LocalTime startTime, LocalTime endTime);

    List<Appointment> findAllByAgent(Agent agent);

    List<Appointment> findAllByAgentAndDate(Agent agent, LocalDate date);

    List<Appointment> findAllByAgentAndDateGreaterThanEqual(Agent agent, LocalDate date);

    default List<Appointment> findAllTodayByAgent(Agent agent) {
        LocalDate date = LocalDate.now();
        return findAllByAgentAndDate(agent, date);
    }

    default List<Appointment> findAllFutureByAgent(Agent agent) {
        LocalDate date = LocalDate.now();
        return findAllByAgentAndDateGreaterThanEqual(agent, date);
    }

}
