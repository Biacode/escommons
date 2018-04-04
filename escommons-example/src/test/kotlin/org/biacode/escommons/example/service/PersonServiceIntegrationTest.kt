package org.biacode.escommons.example.service

import org.apache.commons.lang3.RandomUtils
import org.apache.http.message.BasicHeader
import org.assertj.core.api.Assertions
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import org.biacode.escommons.example.test.AbstractServiceIntegrationTest
import org.biacode.escommons.toolkit.component.EsCommonsClientWrapper
import org.elasticsearch.client.RestHighLevelClient
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import java.util.*

/**
 * Created by Vasil Mamikonyan
 * Company: SFL LLC
 * Date: 4/3/2018
 * Time: 8:56 PM
 */
@Configuration
class PersonServiceIntegrationTest : AbstractServiceIntegrationTest() {

    //region Dependencies
    @Autowired
    private lateinit var esClient: RestHighLevelClient

    @Autowired
    private lateinit var personService: PersonService

    @Autowired
    private lateinit var esCommonsClientWrapper: EsCommonsClientWrapper
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
        val person = buildPersonDocument()
        person.id = UUID.randomUUID().toString()
        Assertions.assertThat(personService.save(person, indexName)).isTrue()
    }

    @Test
    fun `test save multiple documents`() {
        // given
        val indexName = prepareIndex()
        (1..100).map {
            val person = buildPersonDocument()
            person
        }.toList().let { persons ->
            // when
            personService.save(persons, indexName).let {
                // then
                refreshIndex(indexName)
                Assertions.assertThat(it).isTrue()
            }
        }
    }

    @Test
    fun `test delete single document`() {
        // given
        persistPerson().let { (indexName, person) ->
            // when
            val delete = personService.delete(person.id, indexName)
            refreshIndex(indexName)
            // then
            Assertions.assertThat(delete).isTrue()
            // then
            Assertions.assertThat(personService.findById(person.id, indexName).isPresent).isFalse()
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
        personService.delete(persons, indexName).let {
            refreshIndex(indexName)
            // then
            Assertions.assertThat(it).isFalse()
            // then
            Assertions.assertThat(personService.findByIds(persons, indexName).size).isEqualTo(0)
            // then
            Assertions.assertThat(personService.findById(otherPerson, indexName).isPresent).isTrue()
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
        personService.findById(person.id, indexName).let {
            // then
            Assertions.assertThat(it.isPresent).isTrue()
            // then
            Assertions.assertThat(it.get()).isEqualTo(person)
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
        personService.findByIds(personIds, indexName).let {
            // then
            Assertions.assertThat(it).isEqualTo(persons)
        }
    }

    @Test
    fun `test get by person filter documents`() {
        // given
        val matchingName = "AbC"
        val indexName = prepareIndex()
        val matchingPerson1 = persistPerson(firstName = matchingName, indexName = indexName)
        val matchingPerson2 = persistPerson(firstName = matchingName, indexName = indexName)
        val matchingButNotIncludedInThePagePerson3 = persistPerson(firstName = matchingName, indexName = indexName)
        val notMatchingPerson = persistPerson(indexName = indexName)
        val personFilter = PersonFilter(matchingName, 0, 2);
        refreshIndex(indexName)
        // when
        personService.getByFilter(personFilter, indexName).let {
            // then
            Assertions.assertThat(it.totalCount).isEqualTo(3)
            Assertions.assertThat(it.documents.size).isEqualTo(2)
            Assertions.assertThat(it.documents).containsExactlyInAnyOrder(matchingPerson1.second, matchingPerson2.second)
        }
    }
    //endregion

    //region Utility methods
    private fun prepareIndex(): String {
        val indexName = UUID.randomUUID().toString()
        esCommonsClientWrapper.createIndex(indexName, "escommons_test_person")
        return indexName
    }

    private fun buildPersonDocument(
            id: String = UUID.randomUUID().toString(),
            firstName: String = UUID.randomUUID().toString(),
            lastName: String = UUID.randomUUID().toString(),
            age: Int = RandomUtils.nextInt()
    ): Person {
        val person = Person(firstName, lastName, age)
        person.id = id
        return person
    }

    private fun persistPerson(
            id: String = UUID.randomUUID().toString(),
            firstName: String = UUID.randomUUID().toString(),
            lastName: String = UUID.randomUUID().toString(),
            age: Int = RandomUtils.nextInt(),
            indexName: String = prepareIndex()
    ): Pair<String, Person> {
        val person = Person(firstName, lastName, age)
        person.id = id
        personService.save(person, indexName)
        return indexName to person
    }
    //endregion
}