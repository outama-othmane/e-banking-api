package ma.ac.ensa.ebankingapi.controllers;

import ma.ac.ensa.ebankingapi.services.MultipleTransferService;
import ma.ac.ensa.ebankingapi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.APP_ROOT + "/api/multiple_transfers")
public class MultipleTransferController {

    private final MultipleTransferService multipleTransferService;

    @Autowired
    public MultipleTransferController(MultipleTransferService multipleTransferService) {
        this.multipleTransferService = multipleTransferService;
    }

}
