package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AccountAuthorization;
import ma.ac.ensa.ebankingapi.dtos.MultipleTransferDto;
import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.services.AccountService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.APP_ROOT + "/accounts")
public class AccountController {

    private final AccountAuthorization authorization;

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountAuthorization authorization,
                             AccountService accountService) {
        this.authorization = authorization;
        this.accountService = accountService;
    }

    @GetMapping("{id}/multiple_transfers")
    public List<MultipleTransferDto> getClientMultipleTransfer(@PathVariable("id") Account account) {
        // Check if the current user has the role to update the client
        authorization.can("update", account);

        return accountService.getLast10ClientMultipleTransfer(account);
    }
}
