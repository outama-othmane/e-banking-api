package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AccountAuthorization;
import ma.ac.ensa.ebankingapi.dtos.MultipleTransferDto;
import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.services.AccountService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(allowCredentials = "true",  origins = "http://localhost:4200")
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

    @DeleteMapping("{id}")
    public void deleteAccount(@PathVariable("id") Account account) {
        authorization.can("delete", account);

        accountService.deleteAccount(account);
    }

    @GetMapping("{id}/multiple_transfers")
    public List<MultipleTransferDto> getClientMultipleTransfer(@PathVariable("id") Account account) {
        // Check if the current user has the role to update the client
        authorization.can("update", account);

        return accountService.getLast10ClientMultipleTransfer(account);
    }
}
