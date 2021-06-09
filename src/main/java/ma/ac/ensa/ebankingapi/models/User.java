package ma.ac.ensa.ebankingapi.models;

import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.listeners.UserListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@EntityListeners({UserListener.class})
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "DATE")
    private LocalDate birthday;

    @Column(nullable = false, unique = true)
    private String IDCard;

    @Column
    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Client client;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Agent agent;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
