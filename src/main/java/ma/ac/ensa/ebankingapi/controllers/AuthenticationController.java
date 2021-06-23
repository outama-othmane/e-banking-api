package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;
import ma.ac.ensa.ebankingapi.services.AuthenticationService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(allowCredentials = "true",  originPatterns = "*")
@RestController
@RequestMapping(Constants.APP_ROOT + "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public AuthenticationTokenDto login(@Valid @RequestBody AuthenticationDto authenticationDto) {
        return authenticationService.authenticate(authenticationDto);
    }

    @GetMapping("check")
    public ResponseEntity<?> checkAuthentication() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("me")
    public ResponseEntity<?> getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
