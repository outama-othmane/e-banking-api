package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;
import ma.ac.ensa.ebankingapi.exception.InvalidCredentialsException;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.services.AuthenticationService;
import ma.ac.ensa.ebankingapi.services.UserService;
import ma.ac.ensa.ebankingapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtUtil jwtUtil,
                                     UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public AuthenticationTokenDto authenticate(AuthenticationDto authenticationDto) {
        Authentication authResult = null;

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationDto.getUsername(),
                    authenticationDto.getPassword()
            );

            authResult = authenticationManager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

         final User user = (User) authResult.getPrincipal();

         String token = jwtUtil.generateToken(user);

        return AuthenticationTokenDto.builder()
                .token(token)
                .build();
    }
}
