package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.User;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant birthday;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String IDCard;

    private String phoneNumber;

    private AddressDto address;

    private UserRole role;

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            // TODO: throw an exception
            return null;
        }

        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .birthday(userDto.getBirthday())
                .IDCard(userDto.getIDCard())
                .phoneNumber(userDto.getPhoneNumber())
                .address(AddressDto.toEntity(userDto.getAddress()))
                .role(userDto.getRole())
                .build();
        user.setId(userDto.id);
        return user;
    }

    public static UserDto fromEntity(User user) {
        if (user == null) {
            // TODO: throw an exception
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .birthday(user.getBirthday())
                .IDCard(user.getIDCard())
                .phoneNumber(user.getPhoneNumber())
                .address(AddressDto.fromEntity(user.getAddress()))
                .role(user.getRole())
                .build();
    }
}
