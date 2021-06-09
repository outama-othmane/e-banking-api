package ma.ac.ensa.ebankingapi.models.listeners;

import ma.ac.ensa.ebankingapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
public class UserListener {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserListener(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    public void encodePasswordBeforeInsertion(User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
    }
}
