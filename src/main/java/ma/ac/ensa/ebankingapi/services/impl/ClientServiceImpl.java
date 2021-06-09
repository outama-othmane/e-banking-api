package ma.ac.ensa.ebankingapi.services.impl;

import com.google.common.base.Strings;
import ma.ac.ensa.ebankingapi.dtos.*;
import ma.ac.ensa.ebankingapi.enumerations.AccountStatus;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.models.*;
import ma.ac.ensa.ebankingapi.repositories.AccountRepository;
import ma.ac.ensa.ebankingapi.repositories.ClientRepository;
import ma.ac.ensa.ebankingapi.repositories.MultipleTransferRepository;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.ClientService;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final MultipleTransferRepository multipleTransferRepository;


    @Autowired
    public ClientServiceImpl(UserRepository userRepository,
                             ClientRepository clientRepository,
                             AccountRepository accountRepository,
                             PasswordEncoder passwordEncoder,
                             MultipleTransferRepository multipleTransferRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.multipleTransferRepository = multipleTransferRepository;
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
        // User user = client.getUser();

        // Delete the client
        clientRepository.delete(client);

        // Delete the user
        // userRepository.delete(user);

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

        if (user.getRole().equals(UserRole.ADMIN)) {
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
    public List<AccountDto> getClientAccountsList(Client client) {
        return accountRepository.findAllByClient(client)
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void createAccount(Client client,
                              AccountDto accountDto) {
        System.out.println(client);
        Account account = AccountDto.toEntity(accountDto);
        account.setClient(client);

        accountRepository.save(account);
    }

    @Override
    public ResponseEntity<?> createMultipleTransferForClient(Client client,
                                                             MultipleTransferDto multipleTransferDto) {
        // Check if the fromAccountNumber belongs to the current client
        if (! accountRepository.existsByClientAndNumber(client, multipleTransferDto.getAccountNumber())) {
            throw new InvalidFieldException("accountNumber", "The account number doesn't belong to the client.");
        }

        // Assign the fromAccount to multipleTransferDto
        Account fromAccount = null;
        Optional<Account> fromAccountOptional = accountRepository
                .findFirstByNumber(multipleTransferDto.getAccountNumber());
        if (fromAccountOptional.isPresent()) {
            fromAccount = fromAccountOptional.get();
        }
        multipleTransferDto.setFromAccount(AccountDto.fromEntity(fromAccount));

        // Check if the account is active
        assert fromAccount != null;
        if ( !fromAccount.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new InvalidFieldException("accountNumber", "This account is not active.");
        }

        // Check if the amount exists
        Double totalAmount = multipleTransferDto.getMultipleTransferRecipients()
                .stream()
                .map(MultipleTransferRecipientDto::getAmount)
                .reduce(0d, Double::sum);
        multipleTransferDto.setTotalAmount(totalAmount);
        if ( fromAccount.getBalance() < totalAmount) {
            throw new InvalidFieldException("accountNumber", "Your balance is insufficient.");
        }

        // Check if all the recipients exist
        List<Account> recipientAccounts = multipleTransferDto.getMultipleTransferRecipients()
                .stream()
                .map((recipient) -> {
                    // Maybe check if the recipientAccount is ACTIVE TOO
                    return accountRepository.findFirstByNumber(recipient.getAccountNumber())
                            .orElseThrow(() -> new InvalidFieldException("accountNumber", "The given recipients are not all correct"));
                }).collect(Collectors.toList());

        // Assigning the number of recipients
        Integer recipientsCount = multipleTransferDto.getMultipleTransferRecipients()
                .size();
        multipleTransferDto.setRecipientsCount(recipientsCount);

        // Reducing the amount on the account
        Double newBalance = fromAccount.getBalance() - totalAmount;
        fromAccount.setBalance(newBalance);

        accountRepository.save(fromAccount);

        // Creating the multiple transfer
        final MultipleTransfer multipleTransfer = MultipleTransferDto.toEntity(multipleTransferDto);
        multipleTransfer.setMultipleTransferRecipients(null);
        multipleTransferRepository.save(multipleTransfer);

        List<MultipleTransferRecipient> multipleTransferRecipients = MultipleTransferDto.toEntity(multipleTransferDto)
                .getMultipleTransferRecipients()
                .stream()
                .peek(r -> r.setMultipleTransfer(multipleTransfer))
                .collect(Collectors.toList());
        multipleTransfer.setMultipleTransferRecipients(multipleTransferRecipients);

        multipleTransferRepository.save(multipleTransfer);

        // Update the balances of recipients
        for (int i = 0; i < recipientAccounts.size(); i++) {
            Account recipientAccount = recipientAccounts.get(i);
            Double b = recipientAccount.getBalance() +
                    multipleTransferDto.getMultipleTransferRecipients().get(i).getAmount();
            recipientAccount.setBalance(b);
        }

        accountRepository.saveAll(recipientAccounts);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
