package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.ClientRepository;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void updateClient(Client client, UserDto userDto) {
        User user = client.getUser();

        // TODO: Check if current user has the authority to do this action

        // Check if the email is already taken
        if ( ! user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new IllegalStateException("Email is already taken");
            }
        }

    }

    @Override
    public void deleteClient(Client client) {

    }
}
