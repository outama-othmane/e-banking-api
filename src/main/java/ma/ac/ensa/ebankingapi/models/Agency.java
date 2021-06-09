package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "agencies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Agency extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Embedded
    private Address address;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "agency", orphanRemoval = true)
    private List<Agent> agents;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "agency", orphanRemoval = true)
    private List<Client> clients;
}
