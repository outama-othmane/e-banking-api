package ma.ac.ensa.ebankingapi.authorizations;

import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.User;
import ma.ac.ensa.ebankingapi.utils.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class AgentAuthorization extends Authorization<Agent> {

    @Override
    public Boolean create() {
        return false;
    }

    @Override
    public Boolean update(Agent agent) {
        User currentUser = CurrentUser.get();
        User wantedAgentUser = agent.getUser();

        return wantedAgentUser.getId().equals(currentUser.getId());
    }

    @Override
    public Boolean delete(Agent agent) {
        return false;
    }

    @Override
    public Boolean viewAll() {
        return false;
    }

    @Override
    public Boolean view(Agent agent) {
        return false;
    }

    @Override
    public Boolean viewSomeOfEntity(Agent agent) {
       return update(agent);
    }
}
