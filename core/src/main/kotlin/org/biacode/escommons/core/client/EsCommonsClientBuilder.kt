package org.biacode.escommons.core.client

import org.elasticsearch.client.RestHighLevelClient

/**
 * Created by Arthur Asatryan.
 * Date: 3/27/18
 * Time: 4:33 PM
 */
interface EsCommonsClientBuilder {
    fun esCommonsRestClient(): RestHighLevelClient
}