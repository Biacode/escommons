package org.biacode.escommons.persistence.repository

import org.biacode.escommons.core.model.document.AbstractEsDocument
import org.biacode.escommons.core.model.response.DocumentsAndTotalCount
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 3:37 PM
 */
interface EsRepository<T : AbstractEsDocument> {

    /**
     * Saves single document.
     *
     * @param document  the document to save
     * @param indexName the index name where the document should be saved
     * @return the document itself
     */
    fun save(document: T, indexName: String): T

    /**
     * Save all documents.
     *
     * @param documents the documents to save
     * @param indexName the index name where the documents should be saved
     * @return the list of documents itself
     */
    fun save(documents: List<T>, indexName: String): List<T>

    /**
     * Deletes document by id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return the document id itself
     */
    fun delete(id: String, indexName: String): String

    /**
     * Deletes all documents by list of ids.
     *
     * @param ids       the document ids
     * @param indexName the index name
     * @return the list of ids itself
     */
    fun delete(ids: List<String>, indexName: String): List<String>

    /**
     * Finds document by given id.
     *
     * @param id        the document id
     * @param indexName the index name
     * @return the t
     */
    fun findById(id: String, indexName: String): Optional<T>

    /**
     * Finds documents by ids.
     *
     * @param ids       the ids
     * @param indexName the index name
     * @return the founded documents and total count
     */
    fun findByIds(ids: List<String>, indexName: String): DocumentsAndTotalCount<T>

    /**
     * Finds documents by field map.
     *
     * @param searchField  the search field
     * @param terms        the terms
     * @param resultField  the result field
     * @param indexName    the index name
     * @param documentType the document type
     * @return the map where the key is search field and the value is result field
     */
    fun findByField(searchField: String, terms: List<Any>, resultField: String, indexName: String, documentType: String): Map<Any?, Any?>
}
