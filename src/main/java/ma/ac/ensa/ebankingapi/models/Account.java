package ma.ac.ensa.ebankingapi.models;

import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.AccountStatus;
import ma.ac.ensa.ebankingapi.models.listeners.AccountListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@EntityListeners({ AccountListener.class })
public class Account extends AbstractEntity {

    @Column(nullable = false, unique = true, updatable = false)
	private String number;

    private Double balance;

    private String title;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "fromAccount")
    private List<MultipleTransfer> multipleTransfers;
}
