package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.MultipleTransferRecipient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MultipleTransferRecipientDto {

    private Long id;

    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String accountNumber;

    public static MultipleTransferRecipient toEntity(MultipleTransferRecipientDto multipleTransferRecipientDto) {
        if (multipleTransferRecipientDto == null) {
            // TODO: throw an exception
            return null;
        }

        MultipleTransferRecipient multipleTransferRecipient = MultipleTransferRecipient.builder()
                .amount(multipleTransferRecipientDto.getAmount())
                .accountNumber(multipleTransferRecipientDto.getAccountNumber())
                .build();

        multipleTransferRecipient.setId(multipleTransferRecipient.getId());

        return multipleTransferRecipient;
    }

    public static MultipleTransferRecipientDto fromEntity(MultipleTransferRecipient multipleTransferRecipient) {
        if (multipleTransferRecipient == null) {
            // TODO: throw an exception
            return null;
        }

        return MultipleTransferRecipientDto.builder()
                .id(multipleTransferRecipient.getId())
                .accountNumber(multipleTransferRecipient.getAccountNumber())
                .amount(multipleTransferRecipient.getAmount())
                .build();
    }
}
