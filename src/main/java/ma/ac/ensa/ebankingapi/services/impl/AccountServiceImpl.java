package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.MultipleTransferDto;
import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.models.MultipleTransfer;
import ma.ac.ensa.ebankingapi.repositories.MultipleTransferRepository;
import ma.ac.ensa.ebankingapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final MultipleTransferRepository multipleTransferRepository;

    @Autowired
    public AccountServiceImpl(MultipleTransferRepository multipleTransferRepository) {
        this.multipleTransferRepository = multipleTransferRepository;
    }

    @Override
    public List<MultipleTransferDto> getLast10ClientMultipleTransfer(Account account) {
        final List<MultipleTransfer> multipleTransfers = multipleTransferRepository.
                latest10TransfersByFromAccountIdOrRecipientAccountNumber(account.getId(),
                        account.getNumber());

        return multipleTransfers.stream()
                .map(MultipleTransferDto::fromEntity)
                .collect(Collectors.toList());
    }
}
