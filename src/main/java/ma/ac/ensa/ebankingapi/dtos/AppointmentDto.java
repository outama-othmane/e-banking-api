package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.Appointment;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentDto {

    private Long id;

    @NotBlank
    private LocalTime startTime;

    private LocalTime endTime;

    @NotBlank
    private LocalDate date;

    public static Appointment toEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) {
            // TODO: throw an exception
            return null;
        }

        Appointment appointment = Appointment.builder()
                .startTime(appointmentDto.startTime)
                .endTime(appointmentDto.endTime)
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
                .date(appointment.getDate())
                .build();
    }
}
