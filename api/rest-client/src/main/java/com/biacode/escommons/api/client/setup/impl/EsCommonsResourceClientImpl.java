package com.biacode.escommons.api.client.setup.impl;

import com.biacode.escommons.api.client.setup.EsCommonsResourceClient;
import com.biacode.escommons.api.model.common.EsCommonsRequest;
import com.biacode.escommons.api.model.common.EsCommonsResultResponse;
import com.biacode.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.biacode.escommons.api.model.setup.request.PrepareIndexRequest;
import com.biacode.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.biacode.escommons.api.model.setup.response.PrepareIndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:03 PM
 */
public class EsCommonsResourceClientImpl implements EsCommonsResourceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCommonsResourceClientImpl.class);

    //region Constants
    private static final String RESOURCE_BASE_PATH = "escommons";

    private static final String CONTENT_TYPE = "Content-Type";
    //endregion

    //region Properties
    private final Client client;

    private final String apiPath;
    //endregion

    //region Constructors
    public EsCommonsResourceClientImpl(final Client client, final String apiPath) {
        LOGGER.debug("Initializing");
        this.client = client;
        this.apiPath = apiPath;
    }
    //endregion

    //region Public methods
    @Override
    public EsCommonsResultResponse<PrepareIndexResponse> prepareIndex(final PrepareIndexRequest request) {
        assertRequest(request);
        return getBaseWebTarget("prepare-index")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), new GenericType<EsCommonsResultResponse<PrepareIndexResponse>>() {
                });
    }

    @Override
    public EsCommonsResultResponse changeAlias(final ChangeIndexAliasRequest request) {
        assertRequest(request);
        return getBaseWebTarget("change-alias")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), new GenericType<EsCommonsResultResponse>() {
                });
    }

    @Override
    public EsCommonsResultResponse removeIndexByName(final RemoveIndexByNameRequest request) {
        assertRequest(request);
        return getBaseWebTarget("remove-index-by-name")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE), new GenericType<EsCommonsResultResponse>() {
                });
    }
    //endregion

    //region Utility methods
    private <T extends EsCommonsRequest> void assertRequest(final T request) {
        Assert.notNull(request, "The es request should not be null");
    }

    private WebTarget getBaseWebTarget(final String path) {
        return client.target(apiPath).path(RESOURCE_BASE_PATH).path(path);
    }
    //endregion
}
