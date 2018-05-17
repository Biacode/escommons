package org.biacode.escommons.example.persistence.impl

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import org.biacode.escommons.example.persistence.PersonRepository
import org.biacode.escommons.persistence.repository.impl.AbstractEsRepository
import org.biacode.escommons.toolkit.component.SearchResponseComponent
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders.boolQuery
import org.elasticsearch.index.query.QueryBuilders.termQuery
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
        val filterQuery = boolQuery().should(termQuery(FIRST_NAME, filter.firstName))
        val sourceBuilder = SearchSourceBuilder()
        sourceBuilder.query(filterQuery).from(filter.from).size(filter.size)
        val searchRequest = SearchRequest()
        searchRequest.source(sourceBuilder)
        val searchResponse = esCommonsRestClient.search(searchRequest)
        return searchResponseComponent.convertToDocumentsAndTotalCount(searchResponse, Person::class.java)
    }

    override fun getAliasName(): String {
        return ALIAS_NAME
    }
    //endregion

    //region Companion object
    companion object {
        private const val FIRST_NAME = "firstName"
        private const val ALIAS_NAME = "person_index"
    }
    //endregion
}