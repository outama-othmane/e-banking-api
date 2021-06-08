package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Admin extends AbstractEntity {
    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
