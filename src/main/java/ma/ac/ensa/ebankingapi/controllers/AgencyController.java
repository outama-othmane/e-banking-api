package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AgencyAuthorization;
import ma.ac.ensa.ebankingapi.authorizations.AgentAuthorization;
import ma.ac.ensa.ebankingapi.dtos.AgencyDto;
import ma.ac.ensa.ebankingapi.dtos.AgentDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.services.AgencyService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(allowCredentials = "true",  originPatterns = "*")
@RestController
@RequestMapping(Constants.APP_ROOT + "/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    private final AgencyAuthorization authorization;

    private final AgentAuthorization agentAuthorization;

    @Autowired
    public AgencyController(AgencyService agencyService,
                            AgencyAuthorization authorization,
                            AgentAuthorization agentAuthorization) {
        this.agencyService = agencyService;
        this.authorization = authorization;
        this.agentAuthorization = agentAuthorization;
    }

    @PostMapping
    public void createAgency(@RequestBody AgencyDto agencyDto) {
        authorization.can("create");

        agencyService.createAgency(agencyDto);
    }

    @PutMapping("{id}")
    public void updateAgency(@PathVariable("id") Agency agency,
                             @RequestBody AgencyDto agencyDto) {
        authorization.can("update", agency);

        agencyService.updateAgency(agency, agencyDto);
    }

    @DeleteMapping("{id}")
    public void deleteAgency(@PathVariable("id") Agency agency) {
        authorization.can("delete", agency);

        agencyService.deleteAgency(agency);
    }

    @GetMapping
    public List<AgencyDto> getAllAgencies() {
        authorization.can("viewAll");

        return agencyService.getAllAgencies();
    }


    @PostMapping("{id}/agents")
    public void createAgent(
            @PathVariable("id") Agency agency,
            @Valid @RequestBody UserDto userDto) {

        agentAuthorization.can("create");

        agencyService.createAgent(agency, userDto);
    }

    @GetMapping("{id}/agents")
    public List<AgentDto> agencyAgentsList(@PathVariable("id") Agency agency) {

        agentAuthorization.can("viewAll");

        return agencyService.agencyAgentsList(agency)
                .stream()
                .peek(agent -> agent.setAgency(null))
                .collect(Collectors.toList());
    }
}
