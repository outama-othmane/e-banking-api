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

        // Check if the current user is admin
        if (user.getRole().equals(UserRole.ADMIN)) {
            return true;
        }

        // Check if the current user is agent
        // And check if the account belongs to one of his clients
        if (user.getRole().equals(UserRole.AGENT) &&
                account.getClient().getAgent().equals(user.getAgent())) {
            return true;
        }

       // Get the current client
       Client client = user.getClient();
       Client clientAccount = account.getClient();

        return clientAccount.getId().equals(client.getId());
    }

    @Override
    public Boolean delete(Account entity) {
        return isAdmin();
    }

    @Override
    public Boolean viewAll() {
        return false;
    }

    @Override
    public Boolean view(Account account) {
        return false;
    }

    @Override
    public Boolean viewSomeOfEntity(Account account) {
        return false;
    }
}
