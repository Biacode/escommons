package org.biacode.escommons.persistence.repository

import org.apache.http.message.BasicHeader
import org.biacode.escommons.core.test.AbstractEsCommonsIntegrationTest
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.biacode.escommons.toolkit.component.SearchResponseComponent
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders.termQuery
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 5:05 PM
 */
class EsRepositoryIntegrationTest : AbstractEsCommonsIntegrationTest() {

    //region Dependencies
    @Autowired
    private lateinit var esClient: RestHighLevelClient

    @Autowired
    private lateinit var personTestRepository: PersonTestRepository

    @Autowired
    private lateinit var esCommonsClientWrapper: EsCommonsClientWrapper

    @Autowired
    private lateinit var searchResponseComponent: SearchResponseComponent
    //endregion

    //region Test methods
    @Test
    fun `test is alive`() {
        esClient.ping(
                BasicHeader("Content-Type", "application/json"),
                BasicHeader("Accept", "application/json")
        )
    }

    @Test
    fun `test save`() {
        // given
        val indexName = UUID.randomUUID().toString()
        esCommonsClientWrapper.createIndex(indexName, "escommons_test_person")
        val person = Person(UUID.randomUUID().toString())
        person.id = UUID.randomUUID().toString()
        personTestRepository.save(person, indexName)
        val findResult = personTestRepository.findById(person.id, indexName)
        esClient.search(SearchRequest(indexName).types("doc").source(SearchSourceBuilder.searchSource().query(termQuery("firstName", person.firstName))))
        println(findResult)
        val documentsAndTotalCount = searchResponseComponent.convertSearchResponseToDocuments(
                esClient.search(SearchRequest(indexName).types("doc").source(SearchSourceBuilder.searchSource().query(termQuery("firstName", person.firstName)))),
                Person::class.java
        )
        println(documentsAndTotalCount)
        val findByField = personTestRepository.findByField("firstName", listOf(person.firstName), "firstName", indexName, "doc")
        println(findByField)
    }
    //endregion

}