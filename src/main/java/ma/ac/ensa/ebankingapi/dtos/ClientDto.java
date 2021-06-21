package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import ma.ac.ensa.ebankingapi.exception.ConvertDtoToEntityException;
import ma.ac.ensa.ebankingapi.exception.ConvertEntityToDtoException;
import ma.ac.ensa.ebankingapi.models.Client;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
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
            throw new ConvertDtoToEntityException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            ClientDto.class.getName()
                    )
            );
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
            throw new ConvertEntityToDtoException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Client.class.getName()
                    )
            );
        }

        return ClientDto.builder()
                .id(client.getId())
                .user(UserDto.fromEntity(client.getUser()))
                .agent(AgentDto.fromEntity(client.getAgent()))
                .agency(AgencyDto.fromEntity(client.getAgency()))
                .build();
    }
}
