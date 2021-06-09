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

        if (isAdmin()) {
            return true;
        }

        // Check if the current user is an agent
        if ( currentUser.getRole().equals(UserRole.AGENT)) {
            User clientAgentUser = client.getAgent().getUser();

            // Check if the agent of the requested client is the current agent
            return clientAgentUser.getId().equals(currentUser.getId());
        }

        // Check if the current user is a client
        if (currentUser.getRole().equals(UserRole.CLIENT)) {
            User clientUser = client.getUser();

            // Check if the requested client is the current client
            return clientUser.getId().equals(CurrentUser.get().getId());
        }

        return false;
    }

    @Override
    public Boolean delete(Client client) {
        User currentUser = CurrentUser.get();

        if (isAdmin()) {
            return true;
        }

        // Check if the current user is an agent
        if (!currentUser.getRole().equals(UserRole.AGENT)) {
            return false;
        }

        User clientAgentUser = client.getAgent().getUser();
        // Check if the agent of the requested client is the current agent
        return clientAgentUser.getId().equals(currentUser.getId());
    }

    @Override
    public Boolean viewAll() {
        return false;
    }

    @Override
    public Boolean view(Client client) {
        return update(client);
    }

    @Override
    public Boolean viewSomeOfEntity(Client client) {
        return update(client);
    }

    @Override
    public Boolean create() {
        User currentUser = CurrentUser.get();

        // Check if the current user is an agent
        return isAdmin() || currentUser.getRole().equals(UserRole.AGENT);
    }
}
