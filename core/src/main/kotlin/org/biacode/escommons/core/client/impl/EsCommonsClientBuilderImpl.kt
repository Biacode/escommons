package org.biacode.escommons.core.client.impl

import org.apache.http.HttpHost
import org.biacode.escommons.core.client.EsCommonsClientBuilder
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 3/27/18
 * Time: 4:33 PM
 */
@Configuration
class EsCommonsClientBuilderImpl : EsCommonsClientBuilder {

    //region Dependencies
    @Value("\${escommons.host}")
    private lateinit var esCommonsHost: String

    @Value("\${escommons.http.port}")
    private var esCommonsHttpPort: Int? = null
    //endregion

    //region Public methods
    @Bean
    override fun esCommonsRestClient(): RestHighLevelClient {
        return RestHighLevelClient(RestClient.builder(HttpHost.create("http://$esCommonsHost:$esCommonsHttpPort")))
    }
    //endregion

}