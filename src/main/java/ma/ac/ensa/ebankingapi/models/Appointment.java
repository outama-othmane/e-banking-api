package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Appointment extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(columnDefinition = "TIME", name = "start_time")
    private LocalTime startTime;

    @Column(columnDefinition = "TIME", name = "end_time")
    private LocalTime endTime;

    @Column(columnDefinition = "DATE", name = "date")
    private LocalDate date;
}
