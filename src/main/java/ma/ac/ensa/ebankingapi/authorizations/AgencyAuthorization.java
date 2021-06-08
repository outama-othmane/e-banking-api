package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class AgencyAuthorization extends Authorization<Agency> {
    @Override
    public Boolean create() {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.ADMIN);
    }

    @Override
    public Boolean update(Agency entity) {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.ADMIN);
    }

    @Override
    public Boolean delete(Agency entity) {
        return false;
    }

    @Override
    public Boolean viewAll() {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.ADMIN);
    }

    @Override
    public Boolean view(Agency entity) {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.ADMIN);
    }

    @Override
    public Boolean viewSomeOfEntity(Agency entity) {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.ADMIN);
    }
}
