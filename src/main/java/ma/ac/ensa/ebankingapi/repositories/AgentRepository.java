package ma.ac.ensa.ebankingapi.repositories;

import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    List<Agent> findAllByAgency(Agency agency);
}
