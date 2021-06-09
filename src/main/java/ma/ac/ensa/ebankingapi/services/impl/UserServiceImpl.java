package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findFirstByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username %s not found.", username))
                );
    }
}
