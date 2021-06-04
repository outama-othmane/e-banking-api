package ma.ac.ensa.ebankingapi.enumerations;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum UserRole {
    CLIENT,
    AGENT,
    ADMIN;


    public String getFullName() {
        return "ROLE_" + this.name();
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        return Sets.newHashSet(
                new SimpleGrantedAuthority("ROLE_" + this.name())
        );
    }

}
