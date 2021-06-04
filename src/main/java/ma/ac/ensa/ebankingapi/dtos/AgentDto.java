package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Client;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AgentDto extends UserDto {
    
    private AgencyDto agency;

    private List<ClientDto> clients;
    
    public static Agent toEntity(AgentDto agentDto) {
        if (agentDto == null) {
            // TODO: throw an exception
            return null;
        }

        List<Client> clients = agentDto.clients
                .stream()
                .map((clientDto) -> ClientDto.toEntity(clientDto))
                .collect(Collectors.toList());

        Agent agent = Agent.builder()
                .agency(AgencyDto.toEntity(agentDto.getAgency()))
                .clients(clients)
                .build();
        return agent;
    }

    public static AgentDto fromEntity(Agent agent) {
        if (agent == null) {
            // TODO: throw an exception
            return null;
        }

        List<ClientDto> clients = agent.getClients()
                .stream()
                .map((client) -> ClientDto.fromEntity(client))
                .collect(Collectors.toList());

        AgentDto agentDto = AgentDto.builder()
                .agency(AgencyDto.fromEntity(agent.getAgency()))
                .clients(clients)
                .build();

        return agentDto;
    }
}
