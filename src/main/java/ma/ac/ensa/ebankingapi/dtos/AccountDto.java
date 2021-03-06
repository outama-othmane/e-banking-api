package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.AccountStatus;
import ma.ac.ensa.ebankingapi.exception.ConvertDtoToEntityException;
import ma.ac.ensa.ebankingapi.exception.ConvertEntityToDtoException;
import ma.ac.ensa.ebankingapi.models.Account;

import javax.validation.constraints.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;

    @NotBlank
    private String title;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String number;

    @PositiveOrZero
    private Double balance = 0d;

    private AccountStatus status = AccountStatus.ACTIVE;

    public static Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            throw new ConvertDtoToEntityException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            AccountDto.class.getName()
                    )
            );
        }

        Account account = Account.builder()
                .title(accountDto.title)
                .balance(accountDto.balance)
                .status(accountDto.status)
                .build();
        account.setId(accountDto.getId());

        return account;
    }

    public static AccountDto fromEntity(Account account) {
        if (account == null) {
            throw new ConvertEntityToDtoException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Account.class.getName()
                    )
            );
        }

        return AccountDto.builder()
                .id(account.getId())
                .title(account.getTitle())
                .number(account.getNumber())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }
}
