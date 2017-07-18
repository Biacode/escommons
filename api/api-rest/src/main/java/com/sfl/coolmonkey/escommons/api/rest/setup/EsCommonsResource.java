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
import org.springframework.stereotype.Component;

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
        final String newIndexName = indexingComponent.createIndexAndSetupMappings(request.getAlias(), request.getTypes());
        return Response.ok(new EsCommonsResultResponse<>(new PrepareIndexResponse(newIndexName))).build();
    }

    @POST
    @Path("change-alias")
    public Response changeAlias(final ChangeIndexAliasRequest request) {
        indexingComponent.createAliasAndDeleteOldIndices(request.getAlias(), request.getIndexName());
        return Response.ok(new EsCommonsResultResponse<>()).build();
    }

    @POST
    @Path("remove-index-by-name")
    public Response removeIndexByName(final RemoveIndexByNameRequest request) {
        indexingComponent.removeIndexByName(request.getIndexName());
        return Response.ok(new EsCommonsResultResponse<>()).build();
    }

    @GET
    @Path("heartbeat")
    public Response heartbeat() {
        return Response.ok("OK").build();
    }
    //endregion
}
