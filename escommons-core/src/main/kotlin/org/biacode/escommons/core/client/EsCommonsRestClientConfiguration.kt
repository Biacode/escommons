package org.biacode.escommons.core.client

import org.apache.http.HttpHost
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
class EsCommonsRestClientConfiguration {

    //region Dependencies
    @Value("\${escommons.http.host}")
    lateinit var esCommonsHost: String

    @Value("\${escommons.http.port}")
    var esCommonsHttpPort: Int? = null
    //endregion

    //region Public methods
    @Bean
    fun esCommonsRestClient(): RestHighLevelClient {
        return RestHighLevelClient(RestClient.builder(HttpHost.create("http://$esCommonsHost:$esCommonsHttpPort")))
    }
    //endregion

}