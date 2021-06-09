package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.Appointment;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class AppointmentAuthorization extends Authorization<Appointment> {

    @Override
    public Boolean create() {
        User user = CurrentUser.get();

        return user.getRole().equals(UserRole.CLIENT);
    }

    @Override
    public Boolean update(Appointment entity) {
        return false;
    }

    @Override
    public Boolean delete(Appointment entity) {
        return false;
    }

    @Override
    public Boolean viewAll() {
        return false;
    }

    @Override
    public Boolean view(Appointment entity) {
        return false;
    }

    @Override
    public Boolean viewSomeOfEntity(Appointment appointment) {
        return false;
    }
}
