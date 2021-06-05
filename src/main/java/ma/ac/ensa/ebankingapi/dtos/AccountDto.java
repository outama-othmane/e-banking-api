package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.ac.ensa.ebankingapi.enumerations.AccountStatus;
import ma.ac.ensa.ebankingapi.models.Account;

import javax.validation.constraints.NotBlank;

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

    private Double balance;

    @NotBlank
    private AccountStatus status;

    public static Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            // TODO: throw an exception
            return null;
        }

        Account account = Account.builder()
                .title(accountDto.title)
                .number(accountDto.number)
                .balance(accountDto.balance)
                .status(accountDto.status)
                .build();
        account.setId(accountDto.getId());

        return account;
    }

    public static AccountDto fromEntity(Account account) {
        if (account == null) {
            // TODO: throw an exception
            return null;
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
