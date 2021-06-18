package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.models.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthorization extends Authorization<Admin> {

    @Override
    public Boolean create() {
        return isAdmin();
    }

    @Override
    public Boolean update(Admin admin) {
        return isAdmin();
    }

    @Override
    public Boolean delete(Admin admin) {
        return false;
    }

    @Override
    public Boolean viewAll() {
        return isAdmin();
    }

    @Override
    public Boolean view(Admin admin) {
        return isAdmin();
    }

    @Override
    public Boolean viewSomeOfEntity(Admin admin) {
        return isAdmin();
    }
}
