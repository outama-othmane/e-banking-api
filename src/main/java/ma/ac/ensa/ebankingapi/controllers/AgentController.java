package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AgentAuthorization;
import ma.ac.ensa.ebankingapi.dtos.*;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.services.AgentService;
import ma.ac.ensa.ebankingapi.utils.AgentWorkingTime;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public List<AgentDto> getAllAgentsList() {
        authorization.can("viewAll");
        return agentService.getAllAgentsList();
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

    @GetMapping("{id}/working-time")
    public Map<String, Map<String, LocalTime>> agentWorkingTime(@PathVariable Long id) {
        return AgentWorkingTime.get();
    }

    @GetMapping("{id}/appointments")
    public List<AppointmentDto> getAgentAppointmentsList(@PathVariable("id") Agent agent) {
        authorization.viewSomeOfEntity(agent);
        return agentService.getAgentAppointmentsList(agent);
    }
}
