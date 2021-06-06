package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.AppointmentPacks;
import ma.ac.ensa.ebankingapi.models.Appointment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentDto {

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    private LocalTime endTime;

    @NotNull
    @DateTimeFormat( pattern="yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate date;

    @NotNull
    private AppointmentPacks pack = AppointmentPacks.SHORT;

    @JsonIgnoreProperties({"agent", "agency"})
    private ClientDto client;

    public static Appointment toEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            // TODO: throw an exception
            return null;
        }

        Appointment appointment = Appointment.builder()
                .startTime(appointmentDto.startTime)
                .endTime(appointmentDto.getEndTime())
                .date(appointmentDto.date)
                .build();
        appointment.setId(appointmentDto.id);

        return appointment;
    }

    public static AppointmentDto fromEntity(Appointment appointment) {
        if (appointment == null) {
            // TODO: throw an exception
            return null;
        }

        return AppointmentDto.builder()
                .id(appointment.getId())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .pack(null)
                .date(appointment.getDate())
                .client(ClientDto.fromEntity(appointment.getClient()))
                .build();
    }

    public LocalTime getEndTime() {
        if (startTime != null && pack != null)
            return startTime
                .plusMinutes(pack.getDurationInMinutes());

        else
            return endTime;
    }
}
