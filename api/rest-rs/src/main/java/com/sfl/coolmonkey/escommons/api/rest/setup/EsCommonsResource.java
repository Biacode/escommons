package com.sfl.coolmonkey.escommons.api.rest.setup;

import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsRequest;
import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsResultResponse;
import com.sfl.coolmonkey.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.PrepareIndexRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.response.PrepareIndexResponse;
import com.sfl.coolmonkey.escommons.core.component.IndexingComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:12 PM
 */
@Component
@Path("escommons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EsCommonsResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCommonsResource.class);

    //region Dependencies
    @Autowired
    private IndexingComponent indexingComponent;
    //endregion

    //region Constructors
    public EsCommonsResource() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @POST
    @Path("prepare-index")
    public Response prepareIndex(final PrepareIndexRequest request) {
        assertPrepareIndexRequest(request);
        final String newIndexName = indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes());
        return Response.ok(new EsCommonsResultResponse<>(new PrepareIndexResponse(newIndexName))).build();
    }

    @POST
    @Path("change-alias")
    public Response changeAlias(final ChangeIndexAliasRequest request) {
        assertChangeIndexAliasRequest(request);
        indexingComponent.createAliasAndDeleteOldIndices(request.getAlias(), request.getIndexName());
        return Response.ok(new EsCommonsResultResponse<>()).build();
    }

    @POST
    @Path("remove-index-by-name")
    public Response removeIndexByName(final RemoveIndexByNameRequest request) {
        assertRemoveIndexByNameRequest(request);
        indexingComponent.removeIndexByName(request.getIndexName());
        return Response.ok(new EsCommonsResultResponse<>()).build();
    }

    @GET
    @Path("heartbeat")
    public Response heartbeat() {
        return Response.ok("OK").build();
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
