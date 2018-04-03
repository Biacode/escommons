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

    @Test
    fun `test delete multiple documents`() {
        // given
        val indexName = prepareIndex()
        val otherPerson = persistPerson(indexName = indexName).second.id
        val persons = listOf<String>(persistPerson(indexName = indexName).second.id, persistPerson(indexName = indexName).second.id)
        refreshIndex(indexName)
        // when
        personTestRepository.delete(persons, indexName).let {
            refreshIndex(indexName)
            // then
            assertThat(it).isFalse()
            // then
            assertThat(personTestRepository.findByIds(persons, indexName).size).isEqualTo(0)
            // then
            assertThat(personTestRepository.findById(otherPerson, indexName).isPresent).isTrue()
        }
    }

    @Test
    fun `test get single document`() {
        // given
        val indexName = prepareIndex()
        val otherPerson = persistPerson(indexName = indexName).second
        val person = persistPerson(indexName = indexName).second
        refreshIndex(indexName)
        // when
        personTestRepository.findById(person.id, indexName).let {
            // then
            assertThat(it.isPresent).isTrue()
            // then
            assertThat(it.get()).isEqualTo(person)
        }
    }

    @Test
    fun `test get multiple documents`() {
        // given
        val indexName = prepareIndex()
        val otherPerson = persistPerson(indexName = indexName).second
        val persons = listOf<Person>(persistPerson(indexName = indexName).second, persistPerson(indexName = indexName).second)
        val personIds = persons.map { person -> person.id }
        refreshIndex(indexName)
        // when
        personTestRepository.findByIds(personIds, indexName).let {
            // then
            assertThat(it).isEqualTo(persons)
        }
    }
    //endregion

    //region Utility methods
    private fun prepareIndex(): String {
        val indexName = UUID.randomUUID().toString()
        esCommonsClientWrapper.createIndex(indexName, "escommons_test_person")
        return indexName
    }

    private fun persistPerson(
            id: String = UUID.randomUUID().toString(),
            firstName: String = UUID.randomUUID().toString(),
            indexName: String = prepareIndex()
    ): Pair<String, Person> {
        val person = Person(firstName)
        person.id = id
        personTestRepository.save(person, indexName)
        return indexName to person
    }
    //endregion

}