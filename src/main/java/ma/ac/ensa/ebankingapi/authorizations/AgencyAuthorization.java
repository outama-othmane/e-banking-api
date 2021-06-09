package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.models.Agency;
import org.springframework.stereotype.Component;

@Component
public class AgencyAuthorization extends Authorization<Agency> {
    @Override
    public Boolean create() {
        return isAdmin();
    }

    @Override
    public Boolean update(Agency entity) {
        return isAdmin();
    }

    @Override
    public Boolean delete(Agency entity) {
        return isAdmin();
    }

    @Override
    public Boolean viewAll() {
        return isAdmin();
    }

    @Override
    public Boolean view(Agency entity) {
        return isAdmin();
    }

    @Override
    public Boolean viewSomeOfEntity(Agency entity) {
        return isAdmin();
    }
}
