package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByClient(Client client);

    Optional<Account> findFirstByNumber(String fromAccountNumber);

    boolean existsByClientAndNumber(Client client, String number);
}
