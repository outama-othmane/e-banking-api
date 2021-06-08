package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class AccountAuthorization extends Authorization<Account> {
    @Override
    public Boolean create() {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.AGENT);
    }

    @Override
    public Boolean update(Account account) {
        User user = CurrentUser.get();

        // TODO : Check if the role is agent or admin then return true automatically.

        if ( ! user.getRole().equals(UserRole.CLIENT)) {
            return false;
       }

       // Get the current client
       Client client = user.getClient();
       Client clientAccount = account.getClient();

        return clientAccount.getId().equals(client.getId());
    }

    @Override
    public Boolean delete(Account entity) {
        return false;
    }

    @Override
    public Boolean viewAll() {
        return false;
    }

    @Override
    public Boolean view(Account entity) {
        return false;
    }

    @Override
    public Boolean viewSomeOfEntity(Account entity) {
        return false;
    }
}
