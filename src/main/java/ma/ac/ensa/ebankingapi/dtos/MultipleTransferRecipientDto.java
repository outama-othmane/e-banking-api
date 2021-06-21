package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.exception.ConvertDtoToEntityException;
import ma.ac.ensa.ebankingapi.exception.ConvertEntityToDtoException;
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
            throw new ConvertDtoToEntityException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            MultipleTransferRecipientDto.class.getName()
                    )
            );
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
            throw new ConvertEntityToDtoException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            MultipleTransferRecipient.class.getName()
                    )
            );
        }

        return MultipleTransferRecipientDto.builder()
                .id(multipleTransferRecipient.getId())
                .accountNumber(multipleTransferRecipient.getAccountNumber())
                .amount(multipleTransferRecipient.getAmount())
                .build();
    }
}
