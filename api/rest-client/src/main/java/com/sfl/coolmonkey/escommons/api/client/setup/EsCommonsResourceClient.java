package com.sfl.coolmonkey.escommons.api.client.setup;

import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsResultResponse;
import com.sfl.coolmonkey.escommons.api.model.setup.request.ChangeIndexAliasRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.PrepareIndexRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.request.RemoveIndexByNameRequest;
import com.sfl.coolmonkey.escommons.api.model.setup.response.ChangeIndexAliasResponse;
import com.sfl.coolmonkey.escommons.api.model.setup.response.PrepareIndexResponse;
import com.sfl.coolmonkey.escommons.api.model.setup.response.RemoveIndexByNameResponse;

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
