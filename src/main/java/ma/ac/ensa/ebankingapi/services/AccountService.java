package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.MultipleTransferDto;
import ma.ac.ensa.ebankingapi.models.Account;

import java.util.List;

public interface AccountService {
    List<MultipleTransferDto> getLast10ClientMultipleTransfer(Account account);
}
