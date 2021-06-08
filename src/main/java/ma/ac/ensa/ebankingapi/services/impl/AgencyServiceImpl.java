package ma.ac.ensa.ebankingapi.services.impl;

import ma.ac.ensa.ebankingapi.dtos.AgencyDto;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.repositories.AgencyRepository;
import ma.ac.ensa.ebankingapi.services.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;

    @Autowired
    public AgencyServiceImpl(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Override
    public void createAgency(AgencyDto agencyDto) {
        Agency agency = AgencyDto.toEntity(agencyDto);

        agencyRepository.save(agency);
    }

    @Override
    public void updateAgency(Agency agency, AgencyDto agencyDto) {
        Agency updatedAgency = AgencyDto.toEntity(agencyDto);

        agency.setTitle(updatedAgency.getTitle());

        if (updatedAgency.getAddress() != null) {
            agency.setAddress(updatedAgency.getAddress());
        }

        agencyRepository.save(agency);
    }

    @Override
    public List<AgencyDto> getAllAgencies() {
        return agencyRepository.findAll()
                .stream()
                .map(AgencyDto::fromEntity)
                .collect(Collectors.toList());
    }
}
