package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @Column(nullable = false)
    private Integer recipientsCount;

    @Column(nullable = false)
    private Double totalAmount;

    private LocalDateTime transferDate;

    @Column
    private String reason;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "multipleTransfer", orphanRemoval = true)
    private List<MultipleTransferRecipient> multipleTransferRecipients;
}
