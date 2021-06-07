package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "multiple_transfer_recipients")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MultipleTransferRecipient extends AbstractEntity {

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "multiple_transfer_id", nullable = false)
    private MultipleTransfer multipleTransfer;
}
