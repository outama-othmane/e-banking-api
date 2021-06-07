package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.MultipleTransfer;
import ma.ac.ensa.ebankingapi.models.MultipleTransferRecipient;
import org.checkerframework.checker.index.qual.Positive;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
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

    private AccountDto fromAccount;

    @NotBlank
    private String accountNumber;

    private Integer recipientsCount;

    private Double totalAmount;

    private LocalDateTime transferDate = LocalDateTime.now();

    @NotNull
    private List<MultipleTransferRecipientDto> multipleTransferRecipients;

    public static MultipleTransfer toEntity(MultipleTransferDto multipleTransferDto) {
        if (multipleTransferDto == null) {
            // TODO: throw an exception
            return null;
        }

        if (multipleTransferDto.fromAccount == null) {
            // TODO : throw an exception
            return null;
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
                .build();

        multipleTransfer.setId(multipleTransferDto.getId());

        return multipleTransfer;
    }

    public static MultipleTransferDto fromEntity(MultipleTransfer multipleTransfer) {
        if (multipleTransfer == null) {
            // TODO: throw an exception
            return null;
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
                .build();
        return transferDto;
    }
}
