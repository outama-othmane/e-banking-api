package ma.ac.ensa.ebankingapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import ma.ac.ensa.ebankingapi.models.Agent;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AgentDto  {

    private Long id;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private UserDto user;

    private AgencyDto agency;
    
    public static Agent toEntity(AgentDto agentDto) {
        if (agentDto == null) {
            // TODO: throw an exception
            return null;
        }

        Agent agent = Agent.builder()
                .user(UserDto.toEntity(agentDto.getUser()))
                .agency(AgencyDto.toEntity(agentDto.getAgency()))
                .build();

        agent.setId(agentDto.id);

        return agent;
    }

    public static AgentDto fromEntity(Agent agent) {
        if (agent == null) {
            // TODO: throw an exception
            return null;
        }

        return AgentDto.builder()
                .id(agent.getId())
                .user(UserDto.fromEntity(agent.getUser()))
                .agency(AgencyDto.fromEntity(agent.getAgency()))
                .build();
    }
}
