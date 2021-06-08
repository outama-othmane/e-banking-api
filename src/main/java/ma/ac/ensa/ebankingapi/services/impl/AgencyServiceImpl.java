package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.AgencyDto;
import ma.ac.ensa.ebankingapi.dtos.AgentDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.AgencyRepository;
import ma.ac.ensa.ebankingapi.repositories.AgentRepository;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;

    private final UserRepository userRepository;

    private final AgentRepository  agentRepository;

    @Autowired
    public AgencyServiceImpl(AgencyRepository agencyRepository,
                             UserRepository userRepository,
                             AgentRepository agentRepository) {
        this.agencyRepository = agencyRepository;
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public void createAgency(AgencyDto agencyDto) {
        Agency agency = AgencyDto.toEntity(agencyDto);

        agencyRepository.save(agency);
    }

    @Override
    public void updateAgency(Agency agency, AgencyDto agencyDto) {
        Agency updatedAgency = AgencyDto.toEntity(agencyDto);

        agency.setTitle(updatedAgency.getTitle());

        if (updatedAgency.getAddress() != null) {
            agency.setAddress(updatedAgency.getAddress());
        }

        agencyRepository.save(agency);
    }

    @Override
    public List<AgencyDto> getAllAgencies() {
        return agencyRepository.findAll()
                .stream()
                .map(AgencyDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void createAgent(Agency agency, UserDto userDto) {
        // Creating the user
        User user = UserDto.toEntity(userDto);
        user.setRole(UserRole.AGENT);
        user.setId(null);
        userRepository.save(user);

        // Creating the agent
        Agent agent = Agent.builder()
                .agency(agency)
                .user(user)
                .build();
        agentRepository.save(agent);
    }

    @Override
    public List<AgentDto> agencyAgentsList(Agency agency) {
        return agentRepository.findAllByAgency(agency)
                .stream()
                .map(AgentDto::fromEntity)
                .collect(Collectors.toList());
    }
}
