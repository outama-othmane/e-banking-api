package ma.ac.ensa.ebankingapi.dtos;

import ma.ac.ensa.ebankingapi.enumerations.AccountStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountStatusDto {

    @NotNull
    private AccountStatus status;
}
