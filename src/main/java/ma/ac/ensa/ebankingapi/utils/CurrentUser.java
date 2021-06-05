package ma.ac.ensa.ebankingapi.utils;

import ma.ac.ensa.ebankingapi.models.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

    public static User get() {
        User user = null;
        
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof User) {
            user = (User) principal;
        }

        return user;

    }
}
