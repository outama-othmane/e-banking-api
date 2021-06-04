package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    AuthenticationTokenDto authenticate(AuthenticationDto authenticationDto);

    ResponseEntity<?> getCurrentUser();
}
