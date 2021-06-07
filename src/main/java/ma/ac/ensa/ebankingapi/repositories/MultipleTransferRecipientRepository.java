package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.MultipleTransferRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleTransferRecipientRepository extends JpaRepository<MultipleTransferRecipient, Long> {
}
