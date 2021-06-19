package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.MultipleTransferDto;
import ma.ac.ensa.ebankingapi.dtos.UpdateAccountStatusDto;
import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.models.MultipleTransfer;
import ma.ac.ensa.ebankingapi.repositories.AccountRepository;
import ma.ac.ensa.ebankingapi.repositories.MultipleTransferRepository;
import ma.ac.ensa.ebankingapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final MultipleTransferRepository multipleTransferRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              MultipleTransferRepository multipleTransferRepository) {
        this.accountRepository = accountRepository;
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

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public void updateAccountStatus(Account account,
                                    UpdateAccountStatusDto updateAccountStatusDto) {
        account.setStatus(updateAccountStatusDto.getStatus());
        accountRepository.save(account);
    }
}
