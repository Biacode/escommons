package org.biacode.escommons.toolkit.component.impl

import com.fasterxml.jackson.core.type.TypeReference
import io.vavr.control.Try
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.message.BasicHeader
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.biacode.escommons.toolkit.component.JsonComponent
import org.biacode.escommons.toolkit.component.MappingsComponent
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest
import org.elasticsearch.action.support.IndicesOptions
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
class EsCommonsRestClientWrapperImpl : EsCommonsClientWrapper {

    //region Dependencies
    @Autowired
    private lateinit var jsonComponent: JsonComponent

    @Autowired
    private lateinit var esClient: RestHighLevelClient

    @Autowired
    private lateinit var mappingsComponent: MappingsComponent
    //endregion

    //region Public methods
    override fun createIndex(indexName: String, type: String, mappingsName: String): Boolean {
        val request = CreateIndexRequest(indexName)
        request.mapping(type, mappingsComponent.readMappings(mappingsName), XContentType.JSON)
        return esClient.indices().create(request).isAcknowledged
    }

    override fun getIndices(): Set<String> {
        esClient.indices().open(OpenIndexRequest("*"))
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

    override fun refreshIndex(indexName: String) {
        esClient.lowLevelClient.performRequest(
                "post",
                "/$indexName/_refresh",
                BasicHeader("Content-Type", "application/json"),
                BasicHeader("Accept", "application/json")
        )
    }

    override fun addAlias(indexName: String, aliasName: String): Boolean {
        val serializedJson = jsonComponent.serialize(mapOf("actions" to listOf(mapOf("add" to mapOf("index" to indexName, "alias" to aliasName)))), Map::class.java)
        return jsonComponent.deserializeFromInputStreamWithTypeReference(
                esClient.lowLevelClient.performRequest(
                        HttpPost.METHOD_NAME,
                        "/_aliases",
                        mutableMapOf<String, String>(),
                        StringEntity(serializedJson, ContentType.APPLICATION_JSON),
                        BasicHeader("Content-Type", "application/json"),
                        BasicHeader("Accept", "application/json")
                ).entity.content,
                object : TypeReference<Map<String, Boolean>>() {}
        )["acknowledged"]!!
    }

    override fun deleteIndices(vararg indexName: String): Boolean {
        val request = DeleteIndexRequest(*indexName)
        request.indicesOptions(IndicesOptions.lenientExpandOpen())
        return esClient.indices().delete(request).isAcknowledged
    }

    override fun indexExists(indexName: String): Boolean {
        return Try.ofSupplier {
            esClient.lowLevelClient.performRequest(
                    "head",
                    "/$indexName",
                    BasicHeader("Content-Type", "application/json"),
                    BasicHeader("Accept", "application/json")
            )
            return@ofSupplier true
        }.getOrElse(false)
    }
    //endregion
}
