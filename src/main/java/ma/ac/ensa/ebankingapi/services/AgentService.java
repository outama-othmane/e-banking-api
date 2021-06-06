package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AppointmentDto;
import ma.ac.ensa.ebankingapi.dtos.ClientDto;
import ma.ac.ensa.ebankingapi.dtos.PasswordDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Appointment;

import java.util.List;

public interface AgentService {
    List<ClientDto> getAgentClientsList(Agent agent);

    void updateAgent(Agent agent, UserDto userDto);

    void changePassword(Agent agent, PasswordDto passwordDto);

    List<AppointmentDto> getAgentAppointmentsList(Agent agent);
}
