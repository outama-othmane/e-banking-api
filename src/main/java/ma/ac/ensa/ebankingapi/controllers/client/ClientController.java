package ma.ac.ensa.ebankingapi.controllers.client;

import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.services.ClientService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.APP_ROOT + "/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PutMapping("{id}")
    public void updateClient(@PathVariable("id") Client client, @Valid @RequestBody UserDto userDto) {
        clientService.updateClient(client, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable("id") Client client) {
        clientService.deleteClient(client);
    }
}
