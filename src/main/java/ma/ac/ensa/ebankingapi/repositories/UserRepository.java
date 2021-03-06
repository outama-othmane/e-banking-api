package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByIDCard(String IDCard);
}
