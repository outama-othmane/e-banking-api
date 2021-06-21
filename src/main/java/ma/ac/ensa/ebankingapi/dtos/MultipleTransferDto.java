package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ma.ac.ensa.ebankingapi.exception.ConvertDtoToEntityException;
import ma.ac.ensa.ebankingapi.exception.ConvertEntityToDtoException;
import ma.ac.ensa.ebankingapi.models.MultipleTransfer;
import ma.ac.ensa.ebankingapi.models.MultipleTransferRecipient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MultipleTransferDto {

    private Long id;

    @JsonIgnore
    private AccountDto fromAccount;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String accountNumber;

    private Integer recipientsCount;

    private Double totalAmount;

    @NotBlank
    private String reason;

    private LocalDateTime transferDate = LocalDateTime.now();

    @NotNull
    private List<MultipleTransferRecipientDto> multipleTransferRecipients;

    public static MultipleTransfer toEntity(MultipleTransferDto multipleTransferDto) {
        if (multipleTransferDto == null) {
            throw new ConvertDtoToEntityException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            MultipleTransferDto.class.getName()
                    )
            );
        }

        if (multipleTransferDto.fromAccount == null) {
            throw new RuntimeException("FromAccount should not be null for a multipleTransferDto");
        }

        List<MultipleTransferRecipient> multipleTransferRecipients = multipleTransferDto.getMultipleTransferRecipients()
                .stream()
                .map(MultipleTransferRecipientDto::toEntity)
                .collect(Collectors.toList());


        MultipleTransfer multipleTransfer = MultipleTransfer.builder()
                .fromAccount(AccountDto.toEntity(multipleTransferDto.getFromAccount()))
                .transferDate(multipleTransferDto.getTransferDate())
                .recipientsCount(multipleTransferDto.getRecipientsCount())
                .totalAmount(multipleTransferDto.getTotalAmount())
                .multipleTransferRecipients(multipleTransferRecipients)
                .reason(multipleTransferDto.getReason())
                .build();

        multipleTransfer.setId(multipleTransferDto.getId());

        return multipleTransfer;
    }

    public static MultipleTransferDto fromEntity(MultipleTransfer multipleTransfer) {
        if (multipleTransfer == null) {
            throw new ConvertEntityToDtoException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            MultipleTransfer.class.getName()
                    )
            );
        }

        List<MultipleTransferRecipientDto> multipleTransferRecipients = multipleTransfer.getMultipleTransferRecipients()
                .stream()
                .map(MultipleTransferRecipientDto::fromEntity)
                .collect(Collectors.toList());

        MultipleTransferDto transferDto = MultipleTransferDto.builder()
                .id(multipleTransfer.getId())
                .accountNumber(multipleTransfer.getFromAccount().getNumber())
                .fromAccount(AccountDto.fromEntity(multipleTransfer.getFromAccount()))
                .recipientsCount(multipleTransfer.getRecipientsCount())
                .totalAmount(multipleTransfer.getTotalAmount())
                .transferDate(multipleTransfer.getTransferDate())
                .multipleTransferRecipients(multipleTransferRecipients)
                .reason(multipleTransfer.getReason())
                .build();
        return transferDto;
    }
}
