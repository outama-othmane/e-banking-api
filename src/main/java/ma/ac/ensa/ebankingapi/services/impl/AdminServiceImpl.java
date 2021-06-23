package ma.ac.ensa.ebankingapi.services.impl;

import com.google.common.base.Strings;
import ma.ac.ensa.ebankingapi.dtos.AddressDto;
import ma.ac.ensa.ebankingapi.dtos.AdminDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.exception.InvalidFieldException;
import ma.ac.ensa.ebankingapi.models.Address;
import ma.ac.ensa.ebankingapi.models.Admin;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.UserRepository;
import ma.ac.ensa.ebankingapi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updateAdmin(Admin admin, UserDto userDto) {
        User user = admin.getUser();

        // Check if the email is already taken
        if ( ! user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new InvalidFieldException("email", "Email is already taken");
            }
        }
        user.setEmail(userDto.getEmail());

        Address address = AddressDto.toEntity(userDto.getAddress());
        if (address != null) {
            user.setAddress(address);
        }

        if ( ! Strings.isNullOrEmpty(userDto.getFirstName())) {
            user.setFirstName(userDto.getFirstName());
        }

        if ( ! Strings.isNullOrEmpty(userDto.getLastName())) {
            user.setLastName(userDto.getLastName());
        }

        if ( ! Strings.isNullOrEmpty(userDto.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        if ( ! Strings.isNullOrEmpty(userDto.getIDCard())) {
            user.setIDCard(userDto.getIDCard());
        }

        if ( ! Strings.isNullOrEmpty(userDto.getPassword())) {
            user.setPassword(
                    passwordEncoder.encode(
                            userDto.getPassword()
                    )
            );
        }

        userRepository.save(user);
    }

    @Override
    public AdminDto getAdmin(Admin admin) {
        return AdminDto.fromEntity(admin);
    }
}
