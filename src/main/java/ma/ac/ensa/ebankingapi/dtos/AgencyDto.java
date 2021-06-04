package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Client;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AgencyDto {

    private Long id;

    @NotBlank
    private String title;

    private AddressDto address;

    private List<AgentDto> agents;

    private List<ClientDto> clients;

    public static Agency toEntity(AgencyDto agencyDto) {
        if (agencyDto == null) {
            // TODO: throw an exception
            return null;
        }

        List<Agent> agents = agencyDto.agents
                .stream()
                .map((agent) -> AgentDto.toEntity(agent))
                .collect(Collectors.toList());

        List<Client> clients = agencyDto.clients
                .stream()
                .map((client) -> ClientDto.toEntity(client))
                .collect(Collectors.toList());

        Agency agency = Agency.builder()
                .title(agencyDto.title)
                .address(AddressDto.toEntity(agencyDto.address))
                .agents(agents)
                .clients(clients)
                .build();
        return agency;
    }

    public static AgencyDto fromEntity(Agency agency) {
        if (agency == null) {
            // TODO: throw an exception
            return null;
        }

        List<AgentDto> agents = agency.getAgents()
                .stream()
                .map((agent) -> AgentDto.fromEntity(agent))
                .collect(Collectors.toList());

        List<ClientDto> clients = agency.getClients()
                .stream()
                .map((client) -> ClientDto.fromEntity(client))
                .collect(Collectors.toList());

        AgencyDto agencyDto = AgencyDto.builder()
                .title(agency.getTitle())
                .address(AddressDto.fromEntity(agency.getAddress()))
                .agents(agents)
                .clients(clients)
                .build();

        return agencyDto;
    }
}
