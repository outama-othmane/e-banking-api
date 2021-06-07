package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.MultipleTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleTransferRepository extends JpaRepository<MultipleTransfer, Long> {
}
