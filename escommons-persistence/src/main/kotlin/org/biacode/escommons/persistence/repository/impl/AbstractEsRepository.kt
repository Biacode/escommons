package org.biacode.escommons.persistence.repository.impl

import org.biacode.escommons.core.model.document.AbstractEsDocument
import org.biacode.escommons.core.model.document.DocumentConstants
import org.biacode.escommons.persistence.repository.EsRepository
import org.biacode.escommons.toolkit.component.JsonComponent
import org.biacode.escommons.toolkit.component.SearchResponseComponent
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.get.GetRequest
import org.elasticsearch.action.get.MultiGetRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.GenericTypeResolver
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 3:37 PM
 */
@Component
abstract class AbstractEsRepository<T : AbstractEsDocument> : EsRepository<T> {

    //region Dependencies
    @Autowired
    private lateinit var esCommonsRestClient: RestHighLevelClient

    @Autowired
    private lateinit var searchResponseComponent: SearchResponseComponent

    @Autowired
    private lateinit var jsonComponent: JsonComponent
    //endregion

    //region Protected methods
    protected val clazz: Class<T>
    //endregion

    //region Constructors
    init {
        LOGGER.debug("Initializing - {}", javaClass.canonicalName)
        this.clazz = GenericTypeResolver.resolveTypeArgument(javaClass, AbstractEsRepository::class.java) as Class<T>
    }
    //endregion

    //region Abstract Methods
    fun getDocumentType(): String = "doc"
    //endregion

    //region Concrete methods
    override fun save(document: T, indexName: String): Boolean {
        val index = esCommonsRestClient
                .index(IndexRequest(indexName, DOCUMENT_TYPE, document.id).source(jsonComponent.serialize(document, clazz), XContentType.JSON))
        return index.shardInfo.failed <= 0
    }

    override fun save(documents: List<T>, indexName: String): Boolean {
        val bulkRequest = BulkRequest()
        documents.forEach {
            bulkRequest.add(
                    IndexRequest(indexName, DOCUMENT_TYPE, it.id)
                            .source(jsonComponent.serialize(it, clazz), XContentType.JSON)
            )
        }
        return !esCommonsRestClient.bulk(bulkRequest).hasFailures()
    }

    override fun delete(id: String, indexName: String): Boolean {
        return esCommonsRestClient.delete(DeleteRequest(indexName, DOCUMENT_TYPE, id)).shardInfo.failed <= 0
    }

    override fun delete(ids: List<String>, indexName: String): Boolean {
        val bulkRequest = BulkRequest()
        ids.forEach { bulkRequest.add(DeleteRequest(indexName, DOCUMENT_TYPE, it)) }
        return esCommonsRestClient.bulk(bulkRequest).hasFailures()
    }

    override fun findById(id: String, indexName: String): Optional<T> {
        val getResponse = esCommonsRestClient.get(GetRequest(indexName, DOCUMENT_TYPE, id))
        return if (!getResponse.isExists) {
            Optional.empty()
        } else Optional.of(searchResponseComponent.convertGetResponseToDocument(getResponse, clazz))
    }

    override fun findByIds(ids: List<String>, indexName: String): List<T> {
        val multiGetRequest = MultiGetRequest()
        ids.forEach { multiGetRequest.add(indexName, DOCUMENT_TYPE, it) }
        val multiGetResponse = esCommonsRestClient.multiGet(multiGetRequest)
        return multiGetResponse.responses.filter { it.response.isExists }.map { jsonComponent.deserializeFromString(it.response.sourceAsString, clazz) }
    }

    protected fun stringValues(vararg values: Any): Array<out Any> {
        Assert.notNull(values, "The string values should not be null")
        return Arrays.stream(values).map { this.stringValue(it) }.toArray()
    }

    protected fun stringValue(value: Any): String {
        Assert.notNull(value, "The string value should not be null")
        return value.toString()
    }

    protected fun dateValue(date: Any): String {
        Assert.notNull(date, "The date value should not be null")
        return if (date is Date) DocumentConstants.DATE_FORMAT.format(date) else date.toString()
    }

    protected fun dateTimeValue(date: Any): String {
        Assert.notNull(date, "The date value should not be null")
        return if (date is Date) DocumentConstants.DATE_TIME_FORMAT.format(date) else date.toString()
    }

    protected fun decimalValue(decimal: Any): String {
        Assert.notNull(decimal, "The decimal value should not be null")
        return decimal.toString()
    }

    protected fun documentType(): String = DOCUMENT_TYPE
    //endregion

    //region Companion object
    companion object {
        private val LOGGER = LoggerFactory.getLogger(AbstractEsRepository::class.java)
        private const val DOCUMENT_TYPE = "doc"
    }
    //endregion
}
