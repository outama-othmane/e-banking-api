package ma.ac.ensa.ebankingapi.services.impl;

import com.google.common.base.Strings;
import ma.ac.ensa.ebankingapi.dtos.*;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.models.*;
import ma.ac.ensa.ebankingapi.repositories.AgentRepository;
import ma.ac.ensa.ebankingapi.repositories.AppointmentRepository;
import ma.ac.ensa.ebankingapi.repositories.ClientRepository;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.AgentService;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    private final ClientRepository clientRepository;

    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository,
                            ClientRepository clientRepository,
                            AppointmentRepository appointmentRepository,
                            UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<ClientDto> getAgentClientsList(Agent agent) {
        return clientRepository.findAllByAgent(agent)
                .stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAgent(Agent agent, UserDto userDto) {
        User user = agent.getUser();

        // Check if the current user is an admin
        if (CurrentUser.get().getRole().equals(UserRole.ADMIN)) {
            // Check if the idcard changed
            if ( ! user.getIDCard().equals(userDto.getIDCard())) {
                // Check if the idcard is unique
                if (userRepository.existsByIDCard(userDto.getIDCard())) {
                    throw new InvalidFieldException("idcard", "IDCard is already taken");
                }
            }
            user.setIDCard(userDto.getIDCard());

            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
        }

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

        if (! CurrentUser.get().getRole().equals(UserRole.ADMIN)) {
            if ( ! passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
                throw new InvalidFieldException("currentPassword", "The current password is incorrect.");
            }
        }

        user.setPassword(
                passwordEncoder.encode(passwordDto.getNewPassword())
        );

        userRepository.save(user);
    }

    @Override
    public List<AppointmentDto> getAgentAppointmentsList(Agent agent) {
        return appointmentRepository.findAllFutureByAgent(agent)
                .stream()
                .map(AppointmentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentDto> getAllAgentsList() {
        return agentRepository.findAll()
                .stream()
                .map(AgentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAgent(Agent agent) {
        agentRepository.delete(agent);
    }
}
