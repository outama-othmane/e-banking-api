package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AccountAuthorization;
import ma.ac.ensa.ebankingapi.authorizations.ClientAuthorization;
import ma.ac.ensa.ebankingapi.dtos.*;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.services.ClientService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constants.APP_ROOT + "/clients")
public class ClientController {

    private final ClientService clientService;

    private final ClientAuthorization authorization;

    private final AccountAuthorization accountAuthorization;

    @Autowired
    public ClientController(ClientService clientService,
                            ClientAuthorization authorization,
                            AccountAuthorization accountAuthorization) {
        this.clientService = clientService;
        this.authorization = authorization;
        this.accountAuthorization = accountAuthorization;
    }

    @GetMapping("{id}")
    public ClientDto getClient(@PathVariable("id") Client client) {
        authorization.can("view", client);
        return clientService.getClient(client);
    }

    @PostMapping
    public void createClient(@Valid @RequestBody UserDto userDto) {
        authorization.can("create");
        clientService.createClient(userDto);
    }

    @PutMapping("{id}")
    public void updateClient(@PathVariable("id") Client client,
                                       @Valid @RequestBody UserDto userDto) {
        authorization.can("update", client);
        clientService.updateClient(client, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable("id") Client client) {
        authorization.can("delete", client);
        clientService.deleteClient(client);
    }

    @RequestMapping(path = "{id}/password", method = { RequestMethod.POST, RequestMethod.PUT })
    public void changePassword(@PathVariable("id") Client client,
                               @Valid @RequestBody PasswordDto passwordDto) {
        authorization.can("update", client);
        clientService.changePassword(client, passwordDto);
    }

    @GetMapping("{id}/accounts")
    public List<AccountDto> getClientAccountsList(@PathVariable("id") Client client) {
        authorization.can("viewSomeOfEntity", client);
        return clientService.getClientAccountsList(client);
    }

    @PostMapping("{id}/accounts")
    public void createAccount(@PathVariable("id") Client client,
                              @Valid @RequestBody AccountDto accountDto) {
        // Check if the current user has the role to create the account
        accountAuthorization.can("create");

        // Check if the current user has the role to update the client
        authorization.can("update", client);

        clientService.createAccount(client, accountDto);
    }

    @PostMapping("{id}/multiple_transfers")
    public ResponseEntity<?> createMultipleTransfer(@PathVariable("id") Client client,
                                                   @Valid @RequestBody MultipleTransferDto multipleTransfer) {
        // Check if the current user has the role to update the client
        authorization.can("update", client);

        return clientService.createMultipleTransferForClient(client, multipleTransfer);
    }
}
