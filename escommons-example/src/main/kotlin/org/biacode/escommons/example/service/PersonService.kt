package org.biacode.escommons.example.service

import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import org.biacode.escommons.example.domain.Person
import org.biacode.escommons.example.filter.PersonFilter
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 3/29/18
 * Time: 5:24 PM
 */
interface PersonService {

    /**
     * Create person document.
     *
     * @param document  the document
     * @param indexName the index name
     * @return executed successfully
     */
    fun save(document: Person, indexName: String): Boolean

    /**
     * Create multiple person documents
     *
     * @param documents  the documents
     * @param indexName the index name
     * @return executed successfully
     */
    fun save(documents: List<Person>, indexName: String): Boolean


    /**
     * Deletes document by id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return executed successfully
     */
    fun delete(id: String, indexName: String): Boolean

    /**
     * Deletes all documents by list of ids.
     *
     * @param ids       the document ids
     * @param indexName the index name
     * @return the list of ids itself
     */
    fun delete(ids: List<String>, indexName: String): Boolean

    /**
     * Finds document by given id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return the Person
     */
    fun findById(id: String, indexName: String): Optional<Person>

    /**
     * Finds documents by ids.
     *
     * @param ids       the ids
     * @param indexName the index name
     * @return list of founded documents
     */
    fun findByIds(ids: List<String>, indexName: String): List<Person>

    /**
     * Gets persons by person filter
     *
     * @param personFilter the  person filter
     * @param indexName      the index name
     * @return by documents matching person filter
     */
    fun getByFilter(personFilter: PersonFilter, indexName: String): DocumentsAndTotalCount<Person>

}