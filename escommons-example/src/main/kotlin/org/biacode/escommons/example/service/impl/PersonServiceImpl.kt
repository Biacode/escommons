package org.biacode.escommons.example.service.impl

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import org.biacode.escommons.example.persistence.PersonRepository
import org.biacode.escommons.example.service.PersonService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:24 PM
 */
@Component
class PersonServiceImpl : PersonService {

    //region Dependencies
    @Autowired
    private lateinit var personRepository: PersonRepository
    //endregion

    //region Concrete methods
    override fun save(document: Person, indexName: String): Boolean {
        assertPersonDocument(document)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Persisting person document for - {} and index name - {}", document, indexName)
        return personRepository.save(document, indexName)
    }

    override fun save(documents: List<Person>, indexName: String): Boolean {
        assertPersonDocuments(documents)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Persisting multiple person documents for - {} and index name - {}", documents, indexName)
        return personRepository.save(documents, indexName)
    }

    override fun delete(id: String, indexName: String): Boolean {
        assertPersonIdIsNotNull(id)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Deleting person documents for id - {} and index name - {}", id, indexName)
        return personRepository.delete(id, indexName)
    }

    override fun delete(ids: List<String>, indexName: String): Boolean {
        assertPersonIdsIsNotNull(ids)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Deleting multiple person documents for ids - {} and index name - {}", ids, indexName)
        return personRepository.delete(ids, indexName)
    }

    override fun findById(id: String, indexName: String): Optional<Person> {
        assertPersonIdIsNotNull(id)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Retrieve person document for id - {} and index name - {}", id, indexName)
        return personRepository.findById(id, indexName)
    }

    override fun findByIds(ids: List<String>, indexName: String): List<Person> {
        assertPersonIdsIsNotNull(ids)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Retrieve multiple person document for ids - {} and index name - {}", ids, indexName)
        return personRepository.findByIds(ids, indexName)
    }

    override fun getByFilter(personFilter: PersonFilter, indexName: String): DocumentsAndTotalCount<Person> {
        assertPersonFilter(personFilter)
        assertIndexNameIsNotNull(indexName)
        LOGGER.debug("Retrieve multiple person document for filter - {} and index name - {}", personFilter, indexName)
        return personRepository.filter(personFilter, indexName)
    }
    //endregion

    //region Utility methods
    private fun assertPersonFilter(personFilter: PersonFilter) {
        Assert.notNull(personFilter, "The person filter should not be null")
        Assert.isTrue(personFilter.from >= 0, "From should be greater or equal to zero.")
        Assert.isTrue(personFilter.size > 0, "Size should be greater than zero")
    }

    private fun assertPersonIdIsNotNull(id: String) {
        Assert.notNull(id, "The id should not be null")
    }

    private fun assertPersonIdsIsNotNull(ids: List<String>) {
        Assert.notNull(ids, "The person document ids should not be null")
        ids.forEach { id -> assertPersonIdIsNotNull(id) }
    }

    private fun assertIndexNameIsNotNull(indexName: String) {
        Assert.notNull(indexName, "The index name should not be null")
    }

    private fun assertPersonDocument(document: Person) {
        Assert.notNull(document, "The category document should not be null")
    }

    private fun assertPersonDocuments(documents: List<Person>) {
        Assert.notNull(documents, "The person documents should not be null")
        documents.forEach { document -> assertPersonDocument(document) }
    }
    //endregion

    //region Companion object
    companion object {
        private val LOGGER = LoggerFactory.getLogger(PersonServiceImpl::class.java)
    }
    //endregion
}