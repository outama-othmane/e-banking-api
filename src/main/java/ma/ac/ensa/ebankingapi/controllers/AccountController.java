package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AccountAuthorization;
import ma.ac.ensa.ebankingapi.services.AccountService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.APP_ROOT + "/api/accounts")
public class AccountController {

    private final AccountAuthorization authorization;

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountAuthorization authorization,
                             AccountService accountService) {
        this.authorization = authorization;
        this.accountService = accountService;
    }
}
