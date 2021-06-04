package ma.ac.ensa.ebankingapi.models.listeners;

import ma.ac.ensa.ebankingapi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;

public class UserListener {

    private final PasswordEncoder passwordEncode;

    @Autowired
    public UserListener(PasswordEncoder passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    @PrePersist
    public void encodePasswordBeforeInsertion(User user) {
        user.setPassword(
                passwordEncode.encode(user.getPassword())
        );
    }
}
