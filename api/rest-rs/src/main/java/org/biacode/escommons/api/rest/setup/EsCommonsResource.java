package org.biacode.escommons.api.rest.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    //endregion

    //region Constructors
    public EsCommonsResource() {
        LOGGER.debug("Initializing");
    }
    //endregion

    //region Public methods
    @GET
    @Path("heartbeat")
    public Response heartbeat() {
        return Response.ok("OK").build();
    }
    //endregion
}
