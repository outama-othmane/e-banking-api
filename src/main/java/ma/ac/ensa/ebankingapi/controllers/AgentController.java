package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AgentAuthorization;
import ma.ac.ensa.ebankingapi.dtos.ClientDto;
import ma.ac.ensa.ebankingapi.dtos.PasswordDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.services.AgentService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constants.APP_ROOT + "/agents")
public class AgentController {

    private final AgentService agentService;

    private final AgentAuthorization authorization;

    @Autowired
    public AgentController(AgentService agentService,
                           AgentAuthorization authorization) {
        this.agentService = agentService;
        this.authorization = authorization;
    }

    @PutMapping("{id}")
    public void updateAgent(@PathVariable("id") Agent agent,
                                       @Valid @RequestBody UserDto userDto) {
        authorization.can("update", agent);
        agentService.updateAgent(agent, userDto);
    }

    @RequestMapping(path = "{id}/password", method = { RequestMethod.POST, RequestMethod.PUT })
    public void changePassword(@PathVariable("id") Agent agent,
                                       @Valid @RequestBody PasswordDto passwordDto) {
        authorization.can("update", agent);
        agentService.changePassword(agent, passwordDto);
    }

    @GetMapping("{id}/clients")
    public List<ClientDto> getAgentClientsList(@PathVariable("id") Agent agent) {
        authorization.viewSomeOfEntity(agent);
        return agentService.getAgentClientsList(agent);
    }
}
