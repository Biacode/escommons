package org.biacode.escommons.api.rest.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 5:38 PM
 */
@RestController
@RequestMapping(path = "escommons")
public class EsCommonsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCommonsController.class);

    //region Dependencies
    //endregion

    //region Constructors
    public EsCommonsController() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @RequestMapping(path = "heartbeat")
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.ok("OK");
    }
    //endregion
}
