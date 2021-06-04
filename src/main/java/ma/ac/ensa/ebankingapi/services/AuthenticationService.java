package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;

public interface AuthenticationService {
    public AuthenticationTokenDto authenticate(AuthenticationDto authenticationDto);
}
