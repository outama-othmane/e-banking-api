package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class AuthenticationDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
