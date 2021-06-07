package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.AccountDto;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.repositories.AccountRepository;
import ma.ac.ensa.ebankingapi.services.AccountService;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
