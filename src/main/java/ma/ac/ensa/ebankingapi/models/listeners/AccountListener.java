package ma.ac.ensa.ebankingapi.models.listeners;

import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.utils.AccountNumberGenerator;

import javax.persistence.PrePersist;

public class AccountListener {

    @PrePersist
    public void beforeCreatingAccount(Account account) {
        account.setNumber(AccountNumberGenerator.generateAccountNumber());
    }
}
