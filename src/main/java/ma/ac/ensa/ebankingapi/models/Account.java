package ma.ac.ensa.ebankingapi.models;

import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.AccountStatus;
import ma.ac.ensa.ebankingapi.utils.AccountNumberGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Account extends AbstractEntity {

    @Column(nullable = false, unique = true, updatable = false)
	@GeneratedValue(generator = "accountNumberGenerator")
	@GenericGenerator(name = "accountNumberGenerator", strategy = "ma.ac.ensa.ebankingapi.utils.AccountNumberGenerator")
    private String number;

    private Double balance;

    private String title;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "client_id")
    private Client client;

    @PrePersist
    public void beforeCreatingAccount() {
        number = AccountNumberGenerator.generateAccountNumber();
    }
}
