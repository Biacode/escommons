package org.biacode.escommons.api.client.setup;

import org.biacode.escommons.api.model.common.EsCommonsResultResponse;
import org.biacode.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import org.biacode.escommons.api.model.setup.request.PrepareIndexRequest;
import org.biacode.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import org.biacode.escommons.api.model.setup.response.ChangeIndexAliasResponse;
import org.biacode.escommons.api.model.setup.response.PrepareIndexResponse;
import org.biacode.escommons.api.model.setup.response.RemoveIndexByNameResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:03 PM
 */
public interface EsCommonsResourceClient {

    EsCommonsResultResponse<PrepareIndexResponse> prepareIndex(final PrepareIndexRequest request);

    EsCommonsResultResponse<ChangeIndexAliasResponse> changeAlias(final ChangeIndexAliasRequest request);

    EsCommonsResultResponse<RemoveIndexByNameResponse> removeIndexByName(final RemoveIndexByNameRequest request);
}
