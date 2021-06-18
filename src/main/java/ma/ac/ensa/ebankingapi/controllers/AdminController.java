package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AdminAuthorization;
import ma.ac.ensa.ebankingapi.dtos.UserDto;
import ma.ac.ensa.ebankingapi.models.Admin;
import ma.ac.ensa.ebankingapi.services.AdminService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(allowCredentials = "true",  origins = "http://localhost:4200")
@RestController
@RequestMapping(Constants.APP_ROOT + "/admins")
public class AdminController {

    private final AdminService adminService;

    private final AdminAuthorization authorization;

    @Autowired
    public AdminController(AdminService adminService, AdminAuthorization authorization) {
        this.adminService = adminService;
        this.authorization = authorization;
    }

    @PutMapping("{id}")
    public void updateAdmin(@PathVariable("id") Admin admin,
                                       @Valid @RequestBody UserDto userDto) {
        authorization.can("update", admin);
        adminService.updateAdmin(admin, userDto);
    }
}
