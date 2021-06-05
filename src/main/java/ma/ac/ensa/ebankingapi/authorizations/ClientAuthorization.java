package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.enumerations.UserRole;
import ma.ac.ensa.ebankingapi.models.Client;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class ClientAuthorization extends Authorization<Client> {

    @Override
    public Boolean update(Client client) {
        User currentUser = CurrentUser.get();

        // Check if the current user is an agent
        if ( currentUser.getRole().equals(UserRole.AGENT)) {
            User clientAgentUser = client.getAgent().getUser();

            // Check if the agent of the requested client is the current agent
            if (clientAgentUser.getId().equals(currentUser.getId())) {
                return  true;
            }

            return false;
        }

        // Check if the current user is a client
        if (currentUser.getRole().equals(UserRole.CLIENT)) {
            User clientUser = client.getUser();

            // Check if the requested client is the current client
            if (clientUser.getId().equals(CurrentUser.get().getId())){
                return true;
            }

            return false;
        }

        return false;
    }

    @Override
    public Boolean delete(Client client) {
        User currentUser = CurrentUser.get();

        // Check if the current user is an agent
        if (!currentUser.getRole().equals(UserRole.AGENT)) {
            return false;
        }

        User clientAgentUser = client.getAgent().getUser();
        // Check if the agent of the requested client is the current agent
        if (clientAgentUser.getId().equals(currentUser.getId())) {
            return  true;
        }

        return false;
    }

    @Override
    public Boolean viewAll() {
        return false;
    }

    @Override
    public Boolean view(Client entity) {
        return false;
    }

    @Override
    public Boolean viewSomeOfEntity(Client client) {
        User currentUser = CurrentUser.get();
        User clientUser = client.getUser();

        if ( ! currentUser.getId().equals(clientUser.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean create() {
        User currentUser = CurrentUser.get();

        // Check if the current user is an agent
        if ( ! currentUser.getRole().equals(UserRole.AGENT)) {
            return false;
        }

        return true;
    }
}
