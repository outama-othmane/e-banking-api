package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.AgentAppointmentAvailabilityDto;
import ma.ac.ensa.ebankingapi.dtos.AppointmentDto;
import ma.ac.ensa.ebankingapi.enumerations.AppointmentPacks;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Appointment;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.repositories.AppointmentRepository;
import ma.ac.ensa.ebankingapi.services.AppointmentService;
import ma.ac.ensa.ebankingapi.utils.AgentWorkingTime;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentPacks> getAppointmentPacks() {
        return Arrays.stream(AppointmentPacks.values())
                .collect(Collectors.toList());
    }

    @Override
    public void createAppointment(AppointmentDto appointmentDto) {
        Client client = CurrentUser.get().getClient();
        if (!checkingAvailability(client,appointmentDto)) {
            throw new InvalidFieldException("date", "The given date and time are already booked.");
        }

        Appointment appointment = AppointmentDto.toEntity(appointmentDto);
        appointment.setClient(client);
        appointment.setAgent(client.getAgent());

        appointmentRepository.save(appointment);
    }

    @Override
    public ResponseEntity<?> checkIfTheAgentAvailable(AppointmentDto appointmentDto) {
        Client client = CurrentUser.get().getClient();

        if (!checkingAvailability(client,appointmentDto)) {
            throw new InvalidFieldException("date", "The given date and time are already booked.");
        }

        return ResponseEntity.ok(
                AgentAppointmentAvailabilityDto.builder()
                        .available(true)
                        .build()
        );
    }

    private Boolean checkingAvailability(Client client, AppointmentDto appointmentDto) {
        Agent clientAgent = client.getAgent();

        // Check if the agent works on the given dates
        String dayOfWeek = appointmentDto.getDate()
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                .toLowerCase();
        Map<String, LocalTime> workingTimeInDayOfWeek = AgentWorkingTime.get().get(dayOfWeek);

        try {
            if (workingTimeInDayOfWeek.get("start").isAfter(appointmentDto.getStartTime())) {
                return false;
            }
            if (workingTimeInDayOfWeek.get("end").isBefore(appointmentDto.getEndTime())) {
                return false;
            }
        } catch (Exception e) {
            // Just if the workingTimeInDayOfWeek.get("start") == null
            return false;
        }

        // Check if agent is free in the given time
        Boolean isAgentFree = appointmentRepository.existsByAgentIdAndDateAndStartTimeAndEndTime(
                clientAgent.getId(),
                appointmentDto.getDate(),
                appointmentDto.getStartTime().plusMinutes(1),
                appointmentDto.getEndTime()) == null;

        return isAgentFree;
    }
}
