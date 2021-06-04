package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.Client;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ClientDto extends UserDto {

    private AgentDto agent;

    private AgencyDto agency;

    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            // TODO: throw an exception
            return null;
        }

        Client client = Client.builder()
                .agent(AgentDto.toEntity(clientDto.agent))
                .agency(AgencyDto.toEntity(clientDto.agency))
                .build();
        return client;
    }

    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            // TODO: throw an exception
            return null;
        }

        ClientDto clientDto = ClientDto.builder()
                .agent(AgentDto.fromEntity(client.getAgent()))
                .agency(AgencyDto.fromEntity(client.getAgency()))
                .build();
        return clientDto;
    }
}
