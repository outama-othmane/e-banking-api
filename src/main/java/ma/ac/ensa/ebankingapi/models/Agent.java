package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "agents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Agent extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agent", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Client> clients;
}
