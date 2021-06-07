package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.Account;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class AccountAuthorization extends Authorization<Account> {
    @Override
    public Boolean create() {
        User user = CurrentUser.get();

        if ( ! user.getRole().equals(UserRole.AGENT)) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean update(Account entity) {
        return false;
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
