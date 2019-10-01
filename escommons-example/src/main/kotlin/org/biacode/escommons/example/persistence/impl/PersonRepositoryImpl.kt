package org.biacode.escommons.example.persistence.impl

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import org.biacode.escommons.example.persistence.PersonRepository
import org.biacode.escommons.persistence.repository.impl.AbstractEsRepository
import org.biacode.escommons.toolkit.component.SearchResponseComponent
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders.boolQuery
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:23 PM
 */
@Component
class PersonRepositoryImpl : AbstractEsRepository<Person>(), PersonRepository {

    //region Dependencies
    @Autowired
    private lateinit var esCommonsRestClient: RestHighLevelClient

    @Autowired
    private lateinit var searchResponseComponent: SearchResponseComponent
    //endregion

    //region Public methods
    override fun filter(filter: PersonFilter, indexName: String): DocumentsAndTotalCount<Person> {
        val filterQuery = boolQuery().must(matchQuery(FIRST_NAME, filter.firstName))
        val sourceBuilder = SearchSourceBuilder().query(filterQuery).from(filter.from).size(filter.size)
        val searchRequest = SearchRequest(indexName).source(sourceBuilder)
        val searchResponse = esCommonsRestClient.search(searchRequest, RequestOptions.DEFAULT)
        return searchResponseComponent.documentsAndTotalCount(searchResponse, Person::class.java)
    }
    //endregion

    //region Companion object
    companion object {
        private const val FIRST_NAME = "firstName"
    }
    //endregion
}