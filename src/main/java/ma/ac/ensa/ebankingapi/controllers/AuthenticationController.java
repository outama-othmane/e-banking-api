package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.AuthenticationService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequestMapping(Constants.APP_ROOT + "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
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

    /*
    @GetMapping
    public User user() {
        User user = User.builder()
                .firstName("Othmane")
                .lastName("OUTAMA")
                .email("outama.othmane@gmail.com")
                .password("password")
                .phoneNumber("0639385987")
                .IDCard("JK9090")
                .role(UserRole.CLIENT)
                .birthday(Instant.ofEpochMilli(new GregorianCalendar(1999, Calendar.DECEMBER, 5).getTimeInMillis()))
                .build();
        return userRepository.save(user);
    }
     */
}
