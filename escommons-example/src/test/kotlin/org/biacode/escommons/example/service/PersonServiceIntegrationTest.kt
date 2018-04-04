package org.biacode.escommons.example.service

import org.apache.commons.lang3.RandomUtils
import org.apache.http.message.BasicHeader
import org.assertj.core.api.Assertions
import org.biacode.escommons.example.domain.Person
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
    ): Person{
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