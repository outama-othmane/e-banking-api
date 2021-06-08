package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
