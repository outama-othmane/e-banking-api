package ma.ac.ensa.ebankingapi.services.impl;

import com.google.common.base.Strings;
import io.jsonwebtoken.lang.Collections;
import ma.ac.ensa.ebankingapi.dtos.AddressDto;
import ma.ac.ensa.ebankingapi.dtos.ClientDto;
import ma.ac.ensa.ebankingapi.dtos.PasswordDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.models.Address;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.AgentRepository;
import ma.ac.ensa.ebankingapi.repositories.ClientRepository;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository,
                            ClientRepository clientRepository,
                            UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<ClientDto> getAgentClientsList(Agent agent) {
        return clientRepository.findAllByAgent(agent)
                .stream()
                .map((client) -> ClientDto.fromEntity(client))
                .collect(Collectors.toList());
    }

    @Override
    public void updateAgent(Agent agent, UserDto userDto) {
        User user = agent.getUser();

        // Check if the email is already taken
        if ( ! user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new InvalidFieldException("email", "Email is already taken");
            }
        }
        user.setEmail(userDto.getEmail());

        Address address = AddressDto.toEntity(userDto.getAddress());
        if (address != null) {
            user.setAddress(address);
        }

        if ( ! Strings.isNullOrEmpty(userDto.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        userRepository.save(user);
    }

    @Override
    public void changePassword(Agent agent, PasswordDto passwordDto) {
        User user = agent.getUser();

        // TODO: This check is not required for the admin!
        if ( ! passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidFieldException("currentPassword", "The current password is incorrect.");
        }

        user.setPassword(
                passwordEncoder.encode(passwordDto.getNewPassword())
        );

        userRepository.save(user);
    }
}
