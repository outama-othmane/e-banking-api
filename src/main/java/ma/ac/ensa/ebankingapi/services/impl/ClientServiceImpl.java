package ma.ac.ensa.ebankingapi.services.impl;

import com.google.common.base.Strings;
import ma.ac.ensa.ebankingapi.dtos.AccountDto;
import ma.ac.ensa.ebankingapi.dtos.AddressDto;
import ma.ac.ensa.ebankingapi.dtos.PasswordDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.models.*;
import ma.ac.ensa.ebankingapi.repositories.AccountRepository;
import ma.ac.ensa.ebankingapi.repositories.ClientRepository;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.ClientService;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ClientServiceImpl(UserRepository userRepository,
                             ClientRepository clientRepository,
                             AccountRepository accountRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updateClient(Client client, UserDto userDto) {
        User user = client.getUser();

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
    public void deleteClient(Client client) {
        User user = client.getUser();

        // TODO: Delete all the accounts
        // TODO: Delete all the appointments

        // Delete the client
        clientRepository.delete(client);

        // Delete the user
        userRepository.delete(user);

    }

    @Override
    public void createClient(UserDto userDto) {
        // Check if the email is already taken
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidFieldException("email", "Email is already taken");
        }

        // Check if the idcard is already taken
        if (userRepository.existsByIDCard(userDto.getIDCard())) {
            throw new InvalidFieldException("idcard", "IDCard is already taken");
        }

        User user = UserDto.toEntity(userDto);
        user.setRole(UserRole.CLIENT);
        userRepository.save(user);

        Agent agent = CurrentUser.get().getAgent();

        Client client = Client.builder()
                .user(user)
                .agent(agent)
                .agency(agent.getAgency())
                .build();

        clientRepository.save(client);

    }

    @Override
    public void changePassword(Client client, PasswordDto passwordDto) {
        User user = client.getUser();

        // TODO: This check is not required for the admin!
        if ( ! passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidFieldException("currentPassword", "The current password is incorrect.");
        }

        user.setPassword(
                passwordEncoder.encode(passwordDto.getNewPassword())
        );

        userRepository.save(user);
    }

    @Override
    public List<AccountDto> getClientAccountsList(Client client) {
        return accountRepository.findAllByClient(client)
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void createAccount(Client client, AccountDto accountDto) {
        System.out.println(client);
        Account account = AccountDto.toEntity(accountDto);
        account.setClient(client);

        accountRepository.save(account);
    }

}
