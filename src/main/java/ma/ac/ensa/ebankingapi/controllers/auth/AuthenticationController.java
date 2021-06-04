package ma.ac.ensa.ebankingapi.controllers.auth;

import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.TemporalAccessorParser;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("login")
    public AuthenticationTokenDto login(@RequestBody AuthenticationDto authenticationDto) {
        return authenticationService.authenticate(authenticationDto);
    }

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
                .birthday(Instant.ofEpochMilli(new GregorianCalendar(1999, 12, 5).getTimeInMillis()))
                .build();
        return userRepository.save(user);
    }

    @GetMapping("{id}")
    public User user(@PathVariable("id") User user) {
        return user;
    }
}
