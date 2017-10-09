package com.biacode.escommons.api.rest.setup;

import com.biacode.escommons.api.model.common.EsCommonsRequest;
import com.biacode.escommons.api.model.common.EsCommonsResultResponse;
import com.biacode.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.biacode.escommons.api.model.setup.request.PrepareIndexRequest;
import com.biacode.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.biacode.escommons.api.model.setup.response.ChangeIndexAliasResponse;
import com.biacode.escommons.api.model.setup.response.PrepareIndexResponse;
import com.biacode.escommons.api.model.setup.response.RemoveIndexByNameResponse;
import com.biacode.escommons.core.component.IndexingComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
        assertPrepareIndexRequest(request);
        final String newIndexName = indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes(), request.getSettings());
        return ResponseEntity.ok(new EsCommonsResultResponse<>(new PrepareIndexResponse(newIndexName)));
    }

    @RequestMapping(path = "change-alias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<EsCommonsResultResponse<ChangeIndexAliasResponse>> changeAlias(@RequestBody final ChangeIndexAliasRequest request) {
        assertChangeIndexAliasRequest(request);
        indexingComponent.createAliasAndDeleteOldIndices(request.getAlias(), request.getIndexName());
        return ResponseEntity.ok(new EsCommonsResultResponse<>());
    }

    @RequestMapping(path = "remove-index-by-name", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<EsCommonsResultResponse<RemoveIndexByNameResponse>> removeIndexByName(@RequestBody final RemoveIndexByNameRequest request) {
        assertRemoveIndexByNameRequest(request);
        indexingComponent.removeIndexByName(request.getIndexName());
        return ResponseEntity.ok(new EsCommonsResultResponse<>());
    }

    @RequestMapping(path = "heartbeat")
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.ok("OK");
    }
    //endregion

    //region Utility methods
    private void assertPrepareIndexRequest(final PrepareIndexRequest request) {
        assertRequest(request);
        assertAliasNotNull(request.getAlias());
        Assert.notNull(request.getTypes(), "The list of document types should not be null");
    }

    private void assertChangeIndexAliasRequest(final ChangeIndexAliasRequest request) {
        assertRequest(request);
        assertAliasNotNull(request.getAlias());
        assertIndexNameNotNull(request.getIndexName());
    }

    private void assertRemoveIndexByNameRequest(final RemoveIndexByNameRequest request) {
        assertRequest(request);
        assertIndexNameNotNull(request.getIndexName());
    }

    private void assertRequest(final EsCommonsRequest request) {
        Assert.notNull(request, "The request should not be null");
    }

    private void assertIndexNameNotNull(final String indexName) {
        Assert.notNull(indexName, "The index name should not be null");
    }

    private void assertAliasNotNull(final String alias) {
        Assert.notNull(alias, "The alias should not be null");
    }
    //endregion
}
