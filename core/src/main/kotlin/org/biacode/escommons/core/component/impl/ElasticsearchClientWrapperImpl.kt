package org.biacode.escommons.core.component.impl

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.http.message.BasicHeader
import org.biacode.escommons.core.component.ElasticsearchClientWrapper
import org.biacode.escommons.core.component.JsonComponent
import org.biacode.escommons.core.component.MappingsComponent
import org.elasticsearch.action.admin.indices.alias.Alias
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.client.Client
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 7/23/17
 * Time: 9:10 PM
 */
@Component
class ElasticsearchClientWrapperImpl : ElasticsearchClientWrapper {

    //region Dependencies
    private lateinit var esClientd: Client

    @Autowired
    private lateinit var jsonComponent: JsonComponent

    @Autowired
    private lateinit var esClient: RestHighLevelClient

    @Autowired
    private lateinit var mappingsComponent: MappingsComponent
    //endregion

    //region Public methods
    override fun clusterIndices(): Set<String> {
        val content = esClient.lowLevelClient
                .performRequest(
                        "GET",
                        "/_cat/indices",
                        BasicHeader("Content-Type", "application/json"),
                        BasicHeader("Accept", "application/json")
                )
                .entity
                .content
        return jsonComponent.deserializeFromInputStreamWithTypeReference(content, object : TypeReference<List<Map<String, Any>>>() {})
                .map { it -> it["index"] as String }.toSet()
    }

    override fun createIndex(indexName: String, type: String, aliasName: String): Boolean {
        val request = CreateIndexRequest(indexName)
        request.mapping(type, mappingsComponent.readMappings(aliasName, type), XContentType.JSON)
        request.alias(Alias(aliasName))
        val createIndexResponse = esClient.indices().create(request)
        return createIndexResponse.isAcknowledged
    }

    override fun refreshIndex(indexName: String) {
        esClientd.admin().indices().prepareRefresh(indexName).get()
    }

    override fun addAlias(indexName: String, aliasName: String): Boolean {
        return esClientd.admin().indices().prepareAliases().addAlias(indexName, aliasName).get().isAcknowledged
    }

    override fun deleteIndex(indexName: String): Boolean {
        return esClientd.admin().indices().prepareDelete(indexName).get().isAcknowledged
    }

    override fun indexExists(indexName: String): Boolean {
        return esClientd.admin().indices().prepareExists(indexName).get().isExists
    }
    //endregion
}
