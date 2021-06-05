package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.ClientAuthorization;
import ma.ac.ensa.ebankingapi.dtos.PasswordDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.services.ClientService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.APP_ROOT + "/clients")
public class ClientController {

    private final ClientService clientService;

    private final ClientAuthorization authorization;

    @Autowired
    public ClientController(ClientService clientService,
                            ClientAuthorization authorization) {
        this.clientService = clientService;
        this.authorization = authorization;
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
}
