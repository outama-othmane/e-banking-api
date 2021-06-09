package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Client extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "client", orphanRemoval = true)
    private List<Account> accounts;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "client", orphanRemoval = true)
    private List<Appointment> appointments;
}
