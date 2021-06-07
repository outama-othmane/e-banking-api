package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.repositories.AccountRepository;
import ma.ac.ensa.ebankingapi.repositories.MultipleTransferRepository;
import ma.ac.ensa.ebankingapi.services.MultipleTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultipleTransferServiceImpl implements MultipleTransferService {

    private final MultipleTransferRepository multipleTransferRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public MultipleTransferServiceImpl(MultipleTransferRepository multipleTransferRepository,
                                       AccountRepository accountRepository) {
        this.multipleTransferRepository = multipleTransferRepository;
        this.accountRepository = accountRepository;
    }
}
