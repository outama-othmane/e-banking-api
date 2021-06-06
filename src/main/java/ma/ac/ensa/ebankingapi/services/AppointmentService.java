package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AppointmentDto;
import ma.ac.ensa.ebankingapi.enumerations.AppointmentPacks;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentService {
    List<AppointmentPacks> getAppointmentPacks();

    void createAppointment(AppointmentDto appointmentDto);

    ResponseEntity<?> checkIfTheAgentAvailable(AppointmentDto appointmentDto);
}
