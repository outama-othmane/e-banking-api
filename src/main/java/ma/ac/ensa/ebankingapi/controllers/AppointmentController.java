package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.authorizations.AppointmentAuthorization;
import ma.ac.ensa.ebankingapi.dtos.AppointmentDto;
import ma.ac.ensa.ebankingapi.enumerations.AppointmentPacks;
import ma.ac.ensa.ebankingapi.services.AppointmentService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(allowCredentials = "true",  origins = "http://localhost:4200")
@RestController
@RequestMapping(Constants.APP_ROOT + "/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppointmentAuthorization authorization;

    @Autowired
    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentAuthorization authorization) {
        this.appointmentService = appointmentService;
        this.authorization = authorization;
    }

    @GetMapping("packs")
    public List<AppointmentPacks> appointmentPacksList() {
        return appointmentService.getAppointmentPacks();
    }

     @PostMapping
    public void createAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        authorization.can("create");
        appointmentService.createAppointment(appointmentDto);
    }

    @GetMapping("available")
    public ResponseEntity<?> checkIfTheAgentAvailable(@Valid @RequestBody AppointmentDto appointmentDto) {
        authorization.can("create");

        return appointmentService.checkIfTheAgentAvailable(appointmentDto);
    }
}
