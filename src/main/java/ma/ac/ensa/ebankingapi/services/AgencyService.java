package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AgencyDto;
import ma.ac.ensa.ebankingapi.models.Agency;

import java.util.List;

public interface AgencyService {
    void createAgency(AgencyDto agencyDto);

    void updateAgency(Agency agency, AgencyDto agencyDto);

    List<AgencyDto> getAllAgencies();
}
