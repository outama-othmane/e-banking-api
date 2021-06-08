package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "multiple_transfers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MultipleTransfer extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @Column(nullable = false)
    private Integer recipientsCount;

    @Column(nullable = false)
    private Double totalAmount;

    private LocalDateTime transferDate;

    @Column(nullable = true)
    private String reason;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "multipleTransfer")
    private List<MultipleTransferRecipient> multipleTransferRecipients;
}
