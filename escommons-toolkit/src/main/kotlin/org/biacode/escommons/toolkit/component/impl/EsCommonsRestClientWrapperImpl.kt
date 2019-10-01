package org.biacode.escommons.toolkit.component.impl

import com.fasterxml.jackson.core.type.TypeReference
import io.vavr.control.Try
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpHead
import org.apache.http.client.methods.HttpPost
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.biacode.escommons.toolkit.component.JsonComponent
import org.biacode.escommons.toolkit.component.MappingsComponent
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest
import org.elasticsearch.action.support.IndicesOptions
import org.elasticsearch.client.Request
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.settings.Settings
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
    override fun createIndex(indexName: String, mappingsName: String): Boolean {
        val request = CreateIndexRequest(indexName)
        request.mapping(mappingsComponent.readMappings(mappingsName), XContentType.JSON)
        return esClient.indices().create(request, RequestOptions.DEFAULT).isAcknowledged
    }

    override fun createIndex(indexName: String, mappingsName: String, settings: Settings): Boolean {
        val request = CreateIndexRequest(indexName)
                .settings(settings)
                .mapping(mappingsComponent.readMappings(mappingsName), XContentType.JSON)
        return esClient.indices().create(request, RequestOptions.DEFAULT).isAcknowledged
    }

    override fun getIndices(): Set<String> {
        esClient.indices().open(OpenIndexRequest("*"), RequestOptions.DEFAULT)
        val content = esClient.lowLevelClient.performRequest(request("/_cat/indices", HttpGet.METHOD_NAME)).entity.content
        return jsonComponent.deserializeFromInputStreamWithTypeReference(content, object : TypeReference<List<Map<String, Any>>>() {})
                .asSequence()
                .map { it -> it["index"] as String }.toSet()
    }

    override fun refreshIndex(indexName: String) {
        esClient.lowLevelClient.performRequest(request("/$indexName/_refresh", HttpPost.METHOD_NAME))
    }

    override fun addAlias(indexName: String, aliasName: String): Boolean {
        val serializedJson = jsonComponent.serialize(mapOf("actions" to listOf(mapOf("add" to mapOf("index" to indexName, "alias" to aliasName)))))
        val request = request("/_aliases", HttpPost.METHOD_NAME)
        request.setJsonEntity(serializedJson)
        return jsonComponent.deserializeFromInputStreamWithTypeReference(
                esClient.lowLevelClient.performRequest(request).entity.content,
                object : TypeReference<Map<String, Boolean>>() {}
        )["acknowledged"] ?: error("Failed to deserialize `addAlias` response")
    }

    override fun removeAlias(indexName: String, aliasName: String): Boolean {
        val serializedJson = jsonComponent.serialize(mapOf("actions" to listOf(mapOf("remove" to mapOf("index" to indexName, "alias" to aliasName)))))
        val request = request("/_aliases", HttpPost.METHOD_NAME)
        request.setJsonEntity(serializedJson)
        return jsonComponent.deserializeFromInputStreamWithTypeReference(
                esClient.lowLevelClient.performRequest(request).entity.content,
                object : TypeReference<Map<String, Boolean>>() {}
        )["acknowledged"] ?: error("Failed to deserialize `removeAlias` response")
    }

    override fun deleteIndices(vararg indexName: String): Boolean {
        val request = DeleteIndexRequest(*indexName)
        request.indicesOptions(IndicesOptions.lenientExpandOpen())
        return esClient.indices().delete(request, RequestOptions.DEFAULT).isAcknowledged
    }

    override fun indexExists(indexName: String): Boolean = Try
            .ofSupplier {
                esClient.lowLevelClient.performRequest(request("/$indexName", HttpHead.METHOD_NAME))
                return@ofSupplier true
            }
            .getOrElse(false)
    //endregion

    //region Companion object
    companion object {
        private fun jsonHeaders(): RequestOptions {
            val requestOptionsBuilder = RequestOptions.DEFAULT.toBuilder()
            requestOptionsBuilder.addHeader("Content-Type", "application/json")
            requestOptionsBuilder.addHeader("Accept", "application/json")
            return requestOptionsBuilder.build()
        }

        private fun request(endpoint: String, method: String, jsonHeaders: RequestOptions = jsonHeaders()): Request {
            val request = Request(method, endpoint)
            request.options = jsonHeaders
            return request
        }
    }
    //endregion
}
