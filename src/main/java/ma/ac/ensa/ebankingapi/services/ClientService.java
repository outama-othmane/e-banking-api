package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AccountDto;
import ma.ac.ensa.ebankingapi.dtos.MultipleTransferDto;
import ma.ac.ensa.ebankingapi.dtos.PasswordDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
    void updateClient(Client client, UserDto userDto);

    void deleteClient(Client client);

    void createClient(UserDto userDto);

    void changePassword(Client client, PasswordDto passwordDto);

    List<AccountDto> getClientAccountsList(Client client);

    void createAccount(Client client, AccountDto accountDto);

    ResponseEntity<?> createMultipleTransferForClient(Client client, MultipleTransferDto multipleTransfer);
}
