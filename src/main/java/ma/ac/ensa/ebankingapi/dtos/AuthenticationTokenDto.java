package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationTokenDto {

    private String token;
}
