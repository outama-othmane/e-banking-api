package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AgencyAuthorization;
import ma.ac.ensa.ebankingapi.dtos.AgencyDto;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.services.AgencyService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.APP_ROOT + "/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    private final AgencyAuthorization authorization;

    @Autowired
    public AgencyController(AgencyService agencyService,
                            AgencyAuthorization authorization) {
        this.agencyService = agencyService;
        this.authorization = authorization;
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

    @GetMapping
    public List<AgencyDto> getAllAgencies() {
        authorization.can("viewAll");

        return agencyService.getAllAgencies();
    }
}
