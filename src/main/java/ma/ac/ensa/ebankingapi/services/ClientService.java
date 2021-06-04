package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Client;

public interface ClientService {
    void updateClient(Client client, UserDto userDto);

    void deleteClient(Client client);
}
