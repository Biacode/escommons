package com.sfl.coolmonkey.escommons.api.rest.setup;

import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsResultResponse;
import com.sfl.coolmonkey.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.PrepareIndexRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.response.PrepareIndexResponse;
import com.sfl.coolmonkey.escommons.core.component.IndexingComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

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
    @Autowired
    private IndexingComponent indexingComponent;
    //endregion

    //region Constructors
    public EsCommonsController() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @RequestMapping(path = "prepare-index", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<EsCommonsResultResponse<PrepareIndexResponse>> prepareIndex(@RequestBody final PrepareIndexRequest request) {
        final String newIndexName = indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes());
        return ResponseEntity.ok(new EsCommonsResultResponse<>(new PrepareIndexResponse(newIndexName)));
    }

    @RequestMapping(path = "change-alias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<EsCommonsResultResponse> changeAlias(final ChangeIndexAliasRequest request) {
        indexingComponent.createAliasAndDeleteOldIndices(request.getAlias(), request.getIndexName());
        return ResponseEntity.ok(new EsCommonsResultResponse<>());
    }

    @RequestMapping(path = "remove-index-by-name", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<EsCommonsResultResponse> removeIndexByName(final RemoveIndexByNameRequest request) {
        indexingComponent.removeIndexByName(request.getIndexName());
        return ResponseEntity.ok(new EsCommonsResultResponse<>());
    }

    @RequestMapping(path = "heartbeat")
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.ok("OK");
    }
    //endregion
}
