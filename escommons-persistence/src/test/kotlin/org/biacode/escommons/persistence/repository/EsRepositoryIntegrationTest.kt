package org.biacode.escommons.persistence.repository

import org.apache.http.message.BasicHeader
import org.assertj.core.api.Assertions.assertThat
import org.biacode.escommons.core.test.AbstractEsCommonsIntegrationTest
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.biacode.escommons.toolkit.component.SearchResponseComponent
import org.elasticsearch.client.RestHighLevelClient
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/30/18
 * Time: 5:05 PM
 */
@ComponentScan("org.biacode.escommons.persistence.repository")
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
    fun `test save single document`() {
        // given
        val indexName = prepareIndex()
        val person = Person(UUID.randomUUID().toString())
        person.id = UUID.randomUUID().toString()
        assertThat(personTestRepository.save(person, indexName)).isTrue()
    }

    @Test
    fun `test save multiple documents`() {
        // given
        val indexName = prepareIndex()
        (1..100).map {
            val person = Person(UUID.randomUUID().toString())
            person.id = UUID.randomUUID().toString()
            person
        }.toList().let { persons ->
            // when
            personTestRepository.save(persons, indexName).let {
                // then
                refreshIndex(indexName)
                assertThat(it).isTrue()
            }
        }
    }

    @Test
    fun `test delete single document`() {
        // given
        persistPerson().let { (indexName, person) ->
            // when
            val delete = personTestRepository.delete(person.id, indexName)
            refreshIndex(indexName)
            // then
            assertThat(delete).isTrue()
            // then
            assertThat(personTestRepository.findById(person.id, indexName).isPresent).isFalse()
        }
    }
    //endregion

    //region Utility methods
    private fun prepareIndex(): String {
        val indexName = UUID.randomUUID().toString()
        esCommonsClientWrapper.createIndex(indexName, "escommons_test_person")
        return indexName
    }

    private fun persistPerson(): Pair<String, Person> {
        val indexName = prepareIndex()
        val person = Person(UUID.randomUUID().toString())
        person.id = UUID.randomUUID().toString()
        personTestRepository.save(person, indexName)
        return indexName to person
    }
    //endregion

}