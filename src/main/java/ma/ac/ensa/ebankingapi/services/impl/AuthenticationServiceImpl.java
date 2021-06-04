package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.AgentDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationDto;
import ma.ac.ensa.ebankingapi.dtos.AuthenticationTokenDto;
import ma.ac.ensa.ebankingapi.dtos.ClientDto;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.exception.InvalidCredentialsException;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.services.AuthenticationService;
import ma.ac.ensa.ebankingapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationTokenDto authenticate(AuthenticationDto authenticationDto) {
        Authentication authResult;

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

    @Override
    public ResponseEntity<?> getCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();


        if (principal instanceof User) {
            User user = (User) principal;

            if (user.getRole().equals(UserRole.CLIENT)) {
                return new ResponseEntity<>(
                        ClientDto.fromEntity(user.getClient()),
                        HttpStatus.OK
                );
            }

            if (user.getRole().equals(UserRole.AGENT)) {
                return new ResponseEntity<>(
                        AgentDto.fromEntity(user.getAgent()),
                        HttpStatus.OK
                );
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
