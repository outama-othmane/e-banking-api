package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import ma.ac.ensa.ebankingapi.models.Client;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    private Long id;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private UserDto user;

    @JsonIgnoreProperties({"address", "agency"})
    private AgentDto agent;

    private AgencyDto agency;

    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            // TODO: throw an exception
            return null;
        }

        Client client = Client.builder()
                .user(UserDto.toEntity(clientDto.user))
                .agent(AgentDto.toEntity(clientDto.agent))
                .agency(AgencyDto.toEntity(clientDto.agency))
                .build();
        client.setId(client.getId());

        return client;
    }

    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            // TODO: throw an exception
            return null;
        }

        return ClientDto.builder()
                .id(client.getId())
                .user(UserDto.fromEntity(client.getUser()))
                .agent(AgentDto.fromEntity(client.getAgent()))
                .agency(AgencyDto.fromEntity(client.getAgency()))
                .build();
    }
}
