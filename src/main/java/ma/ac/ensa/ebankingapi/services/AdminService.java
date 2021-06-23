package ma.ac.ensa.ebankingapi.services;

import ma.ac.ensa.ebankingapi.dtos.AdminDto;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Admin;

public interface AdminService {
    void updateAdmin(Admin admin, UserDto userDto);

    AdminDto getAdmin(Admin admin);
}
